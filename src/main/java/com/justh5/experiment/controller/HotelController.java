package com.justh5.experiment.controller;

import com.justh5.experiment.domain.CommonHelper;
import com.justh5.experiment.domain.SysResult;
import com.justh5.experiment.model.HotelModel;
import com.justh5.experiment.model.RoomModel;
import com.justh5.experiment.model.UserModel;
import com.justh5.experiment.service.HotelService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("experiment")
public class HotelController {
    @Value("${path}")
    String urlpath;
    private static Logger logger = LogManager.getLogger(HotelController.class);
    @Autowired
    private HotelService hotelService;

    @RequestMapping(value="getHotelList", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SysResult gethotellist() throws SQLException {
        List<HotelModel> hotelModelList= hotelService.getHotelList();
        return new SysResult(1, "success",hotelModelList);
    }
    @RequestMapping(value="getHotelById", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SysResult gethotelbyid(Integer hotelid) throws SQLException {
        if(hotelid>0) {
            HotelModel hotelModel = hotelService.getHotelById(hotelid);
            List<RoomModel> roomModelList = hotelService.getRoomListByHotelId(hotelid);
            Map<String,Object> map=new HashMap<>();
            map.put("roomlist",roomModelList);
            map.put("experiment",hotelModel);
            return new SysResult(1, "success",map);
        }
        return new SysResult(99, "fail");
    }
    @RequestMapping(value="getRoomListByHotelId", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SysResult getroomlistbyhotelid(Integer hotelid) throws SQLException {
        List<RoomModel> roomModelList= hotelService.getRoomListByHotelId(hotelid);
        return new SysResult(1, "success",roomModelList);
    }
    @RequestMapping(value="getHotRoom", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SysResult gethotroom() throws SQLException {
        List<RoomModel> roomModelList= hotelService.getHotRoomList();
        return new SysResult(1, "success",roomModelList);
    }
    @RequestMapping(value="getCount", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SysResult getcount() throws SQLException {
        List<Integer> countlist= hotelService.getCount();
        return new SysResult(1, "success",countlist);
    }
    @CrossOrigin
    @RequestMapping("updateHotel")
    public Object jumpToView(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        //回传用户信息
        modelAndView.setViewName("experiment/login");
        return modelAndView;
    }

    @CrossOrigin
    @RequestMapping("index")
    public Object index(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        //回传用户信息
        modelAndView.setViewName("experiment/index");
        return modelAndView;
    }
    @CrossOrigin
    @RequestMapping("experiment")
    public Object hotel(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        //回传用户信息
        modelAndView.setViewName("experiment/experiment");
        return modelAndView;
    }
    @RequestMapping(value="addhotel", produces = MediaType.APPLICATION_JSON_VALUE,method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public SysResult addhotel(HttpServletRequest request, String hotelname, String address, String ishot, String note,Integer id) throws SQLException {
        HotelModel hotelModel=new HotelModel();
        hotelModel.setAddress(address);
        hotelModel.setHotelname(hotelname);
        hotelModel.setIshot(ishot=="on"?1:0);
        hotelModel.setNote(note);
        if(id>0){
            hotelModel.setId(id);
        }
        String path=urlpath;//"F:\\javaspace\\javaprogect\\git\\experiment\\src\\main\\resources\\static\\pic\\";// request.getServletPath();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> fileList = multipartRequest.getFiles("pic");
        if(fileList == null || fileList.size() == 0){
            return new SysResult(99, "文件错误");
        }
        MultipartFile file = fileList.get(0);
        String filename= CommonHelper.getRandomString(18)+".jpg";
        hotelModel.setPic("/pic/"+filename);
        try {
            savePic(file.getInputStream(),path, filename);
        }catch (Exception ex){

        }
        if(hotelModel!=null&&hotelModel.getHotelname()!=null) {
            if(id>0){
                hotelService.updateHotel(hotelModel);
            }else {
                Integer uid = hotelService.addHotel(hotelModel);
            }
            return new SysResult(1, "success");
        }else{
            return new SysResult(99, "fail");
        }
    }
    @RequestMapping(value="addroom", produces = MediaType.APPLICATION_JSON_VALUE,method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public SysResult addroom(HttpServletRequest request, String roomname, String price, String ishot, String note,String hotelid,String timeprice) throws SQLException {
        RoomModel roomModel=new RoomModel();
        roomModel.setHotelid(Integer.parseInt(hotelid));
        roomModel.setRoomname(roomname);
        roomModel.setRoomcode("");
        roomModel.setPrice(Integer.parseInt(price));
        roomModel.setIshot(ishot=="on"?1:0);
        roomModel.setNote(note);
        roomModel.setRoomstatus(0);
        roomModel.setTimeprice(timeprice);
        String path=urlpath;// request.getServletPath();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> fileList = multipartRequest.getFiles("pic");
        if(fileList == null || fileList.size() == 0){
            return new SysResult(99, "文件错误");
        }
        MultipartFile file = fileList.get(0);
        String filename= CommonHelper.getRandomString(18)+".jpg";
        roomModel.setPic("/pic/"+filename);
        try {
            savePic(file.getInputStream(),path, filename);
        }catch (Exception ex){

        }
        if(roomModel!=null) {
            Integer uid = hotelService.addRoom(roomModel);
            return new SysResult(1, "success");
        }else{
            return new SysResult(99, "fail");
        }
    }

    private void savePic(InputStream inputStream,String path, String fileName) {

        OutputStream os = null;
        try {
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件

            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value="delroom", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SysResult delroom(Integer id) throws SQLException {
        if(id>0) {
            Integer uid = hotelService.delRoom(id);
            return new SysResult(1, "success");
        }else{
            return new SysResult(99, "fail");
        }
    }

    @RequestMapping(value="delhotel", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SysResult delhotel(Integer id) throws SQLException {
        if(id>0) {
            Integer uid = hotelService.delHotel(id);
            return new SysResult(1, "success");
        }else{
            return new SysResult(99, "fail");
        }
    }
}
