package com.sunhuwh.music.http;

/**
 * Created by junxue.rao on 2016/2/29.
 * HTTP相关常量
 */
public class HttpConstants {

    /**
     * API URL
     */
    public static final String API_URL = "http://mobile.renminyixue.com/";
    //public static final String API_URL = "http://192.168.81.24:8084/";

    /**
     * 更新检测
     */
    public static final String GET_UPDATE_JSON = "http://update.renminyixue.com/android/update.json";
    //public static final String GET_UPDATE_JSON = "http://192.168.81.63/update.renminyixue.com/android/update.json";

    /**
     * 网页更新地址
     */
    public static final String WEB_UPDATE_URL = "http://study.91open.com/renminyixue/mobile/h5";

    /**
     * 发现
     */
    public static final String GET_EXPLORE = "explore";

    /**
     * 试听课程分类
     */
    public static final String ALL_CATEGORIES = "all_course_categories";

    /**
     * 子课程
     */
    public static final String ONE_CATEGORY = "classes?course_category_id={0}";

    /**
     * 子课程
     */
    public static final String ONE_COURSE = "chapters?class_id={0}";

    /**
     * 带问题子课程
     */
    public static final String ONE_COURSE_HAS_PROBLEM = "chapters?class_id={0}&filter_name=superChapterHasProblem";

    /**
     * 课程问题
     */
    public static final String CHAPTER_PROBLEM = "problem/list?chapter_id={0}";

    /**
     * 课程随机问题
     */
    public static final String CHAPTER_PROBLEM_RANDOM = "problem/random/list?chapter_id={0}";

    /**
     * 课程错误问题
     */
    public static final String CHAPTER_PROBLEM_INCORRECT = "problem/incorrect/list?chapter_id={0}&page={1}";

    /**
     * 课程错误问题根据状态
     */
    public static final String CHAPTER_PROBLEM_INCORRECT_WITH_STATUS = "problem/incorrect/list?chapter_id={0}&status={1}&page={2}";

    /**
     * 错题总数
     */
    public static final String CHAPTER_PROBLEM_INCORRECT_COUNT = "problem/incorrect/count?chapter_id={0}&page={1}";

    /**
     * 错题总数
     */
    public static final String CHAPTER_PROBLEM_INCORRECT_COUNT_WITH_STATUS = "problem/incorrect/count?chapter_id={0}&status={1}&page={2}";


    /**
     * 子课程进度
     */
    public static final String ONE_PROGRESS = "progress?class_id={0}";

    /**
     * 试听的视频请求
     */
    public static final String TRIAL_VIDEO = "video/audition_path?chapter_id={0}";

    /**
     * 正式的视频请求
     */
    public static final String FORMAL_VIDEO = "video/path?chapter_id={0}";

    /**
     * 我的课程
     */
    public static final String GET_MYCLASSES = "myclasses";

    /**
     * 带问题课程
     */
    public static final String GET_MYCLASSES_HAS_PROBLEM = "myclasses?filterName=classHasProblem";

    /**
     * 热门新闻
     */
    public static final String GET_NEWS_HOT = "news/hot";

    /**
     * 最近新闻
     */
    public static final String GET_NEWS_LATEST = "news/latest?page={0,number,#}";

    /**
     * 医师
     */
    public static final String GET_NEWS_DOCTOR = "news?category_code=yishi&page={0,number,#}&distributor_id={1}";

    /**
     * 护士
     */
    public static final String GET_NEWS_NURSE = "news?category_code=hushi&page={0,number,#}&distributor_id={1}";

    /**
     * 获取课程的购买状态
     */
    public static final String GET_CLASS_PURCHASE_STATUS = "purchase/status?class_id={0}";

    /**
     * 课程购买
     */
    public static final String CLASS_PURCHASE = "purchase?class_id={0}";

    /**
     * 取余额
     */
    public static final String GET_COIN_BALANCE = "coin/balance";

    /**
     * 取充值产品
     */
    public static final String GET_COIN_PRODUCTS = "coin/products?type=APPLE";

    /**
     * 取充ORDER INFO
     */
    public static final String GET_ORDER_INFO = "coin/deposit?product_id={0}";

    /**
     * 药师
     */
    public static final String GET_NEWS_MEDICINE = "news?category_code=yaoshi&page={0,number,#}&distributor_id={1}";


    /**
     * 新闻分类
     */
    public static final String[] NEWS_CATALOGS = new String[]{GET_NEWS_LATEST,GET_NEWS_DOCTOR,GET_NEWS_NURSE,GET_NEWS_MEDICINE};

    /**
     * 课程情况POST
     */
    public static final String POST_PROGRESS_AUTH = "progress";

    /**
     * 登录授权
     */
    public static final String POST_AUTHENTICATE = "authenticate";

    /**
     * 注册
     */
    public static final String POST_REGISTER = "register";

    /**
     * 发送短信
     */
    public static final String POST_CAPTCHA_SEND = "captcha/send";

    /**
     * 获取经销商列表
     */
    public static final String GET_DISTRIBUTORS = "distributors";

    /**
     * 获取个人信息需auth
     */
    public static final String GET_PROFILE_AUTH = "profile";

    /**
     * 获取我的答疑
     */
    public static final String GET_QUESTION_AUTH = "myquestions?page={0,number,#}";

    /**
     * 提交我的答疑
     */
    public static final String POST_QUESTION_AUTH = "question";
}
