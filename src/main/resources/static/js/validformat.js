
function clearNoNum(obj) {
    obj.value = obj.value.replace(/[^\d]/g, "");  //清除“数字”和“.”以外的字符
    obj.value = obj.value.replace(/^\./g, "");  //验证第一个字符是数字而不是.
    obj.value = obj.value.replace(/\.{2,}/g, "."); //只保留第一个. 清除多余的.
    var dIndex = obj.value.indexOf('.');
    if (obj.value.length > 1 && dIndex < 0) {
        obj.value = ltrim(obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", "."), '0');
    }
    obj.value = ltrim(obj.value, '.');
    obj.value = obj.value.replace("..", ".");
    if (dIndex < obj.value.length - 3 && dIndex > -1) {
        obj.value = obj.value.substring(0, obj.value.length - 1);
    }
    //obj.value = parseFloat(obj.value).toFixed(2);
    //CalcSum();
};
function ltrim(s, t) {
    var result = "";
    var len = s.length;
    for (var i = 0; i < len; i++) {
        if (s[0] == t) {
            if (s.length == 0) {
                result = "";
            }
            else {
                s = s.substring(1)
            }
        }
        else {
            result = s;
        }
    }
    return result;
    //return s.replace(/(^s*)/g, "");
}