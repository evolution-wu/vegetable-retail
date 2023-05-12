package com.spring.afterend.common;
//与前端交互得结果类，呃呃呃，算是个标准模板吧
public class Result<T>  {
        private String code;   //结果判断是否错   0是success，其他就是error
        private String msg;   //信息
        private T data;   //数据

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Result() {
        }

        public Result(T data) {
            this.data = data;
        }

        public static Result success() {
            Result result = new Result<>();
            result.setCode("0");
            result.setMsg("成功");
            return result;
        }

        public static <T> Result<T> success(T data) {
            Result<T> result = new Result<>(data);
            result.setCode("0");
            result.setMsg("成功");
            return result;
        }

        public static Result error(String code, String msg) {
            Result result = new Result();
            result.setCode(code);
            result.setMsg(msg);
            return result;
        }
    }


