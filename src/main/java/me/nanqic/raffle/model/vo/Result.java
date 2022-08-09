package me.nanqic.raffle.model.vo;

/**
 * @description:封装json对象，所有返回结果都使用它
 **/
public class Result<T> {

    private int code;// 业务自定义状态码
    private String msg;// 请求状态描述，调试用
    private T data;// 请求数据，对象或数组均可
    private long count;// 数据条数

    public Result() {
        this.msg = "SUCCESS";
        this.code = 200;
    }

    /**
     * 成功时候的调用
     * 无参构造，不返回数据
     * @return Result
     */
    public static  Result ok(){
        return new Result();
    }
    /**
     * 成功时候的调用
     * @param data data
     * @param <T> t
     * @return Result
     */

    public static <T> Result<T> success(T data, Long count){
        return new Result<T>(data,count);
    }

    /**
     * 失败时候的调用
     * @param codeMsg codeMsg
     * @param <T> t
     * @return Result
     */
    public static <T> Result<T> error(me.nanqic.raffle.model.vo.CodeMsg codeMsg){
        return new Result<T>(codeMsg);
    }

    /**
     * 成功的构造函数
     * @param data data
     */
    public Result(T data){
        this.code = 0;//默认0是成功
        this.msg = "SUCCESS";
        this.data = data;
    }
    public Result(T data, long count){
        this.code = 0;//默认0是成功
        this.msg = "SUCCESS";
        this.data = data;
        this.count = count;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 失败的构造函数
     * @param codeMsg codeMsg
     */
    private Result(me.nanqic.raffle.model.vo.CodeMsg codeMsg) {
        if(codeMsg != null) {
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getMsg();
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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

    public long getCount() {
        return count;
    }
    public void setCont(long count) {
        this.count = count;
    }
    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}