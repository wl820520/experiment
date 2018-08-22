package com.justh5.experiment.domain.result;

import java.util.List;

//{
//    "PreviewModel":[
//        {
//            "id":1,
//            "answer":[
//               {name:"A"}
//            ]
//        },
//        {
//            "id":2,
//            "answer":[
//                "A",
//                "B"
//            ]
//        }
//    ],
//    "formal":[
//        {
//            "id":1,
//            "answer":[
//                "A"
//            ]
//        },
//        {
//            "id":2,
//            "value":"502"
//        },
//        {
//            "id":3,
//            "array":[
//                [
//                    {
//                        "id":1,
//                        "value":"400"
//                    },
//                    {
//                        "id":2,
//                        "value":"210"
//                    },
//                    {
//                        "id":3,
//                        "value":"110"
//                    }
//                ]
//            ]
//        }
//    ]
//}
public class JsonResultModel {
    private List<PreviewModel> preview;
    private List<FormalModel> formal;

    public List<PreviewModel> getPreview() {
        return preview;
    }

    public void setPreview(List<PreviewModel> preview) {
        this.preview = preview;
    }

    public List<FormalModel> getFormal() {
        return formal;
    }

    public void setFormal(List<FormalModel> formal) {
        this.formal = formal;
    }
}
