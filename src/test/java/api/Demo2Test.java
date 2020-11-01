package api;

import com.tangym.BaseCase;
import com.tangym.annotations.Api;
import com.tangym.constant.Const;
import com.tangym.dataparser.ExcelDataParser;
import com.tangym.utils.JsonUtil;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

@Api(name = "module2") /* name 对应测试数据表格名 */
public class Demo2Test extends BaseCase { /* 测试脚本要继承BaseCase */
    /**
     * 这是一个使用了路径参数的例子, 也是一个使用 hamcrest 断言的例子，也是一个使用了json schema断言的例子
     */
    @Test(dataProvider = Const.PROVIDER_DATA, description = "豆瓣查询用户信息接口") /* description 描述测试case，更多参数发掘查看@Test注解源码 */
    public void queryUser(Map<String, String> providerParams) { /* 方法名与excel中sheet表名对应 */
        /* 解析测试数据 */
        ExcelDataParser dataParser = new ExcelDataParser();
        dataParser.parseProviderParams(providerParams);

        /* 请求接口 */
        RestAssured.baseURI = "https://api.douban.com"; //因为演示使用了多个不同的baseURI，所以需要重新赋值覆盖env.yml中默认的baseURI
        Response response = given().pathParam("name", dataParser.requestParams.get("name")).get(dataParser.commonParams.get(Const.URL).toString());

        /* 结果hamcrest断言 */
        assertThat(response.getBody().asString(), containsString((String) dataParser.commonParams.get(Const.EXCEPT_RESULT)));

        /* 结果json schema断言 */
        JsonUtil.matchesJsonSchema(response, "jsonschema/user_schema.json");

    }
}
