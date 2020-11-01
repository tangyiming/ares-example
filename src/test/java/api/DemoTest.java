package api;

import com.tangym.BaseCase;
import com.tangym.annotations.Api;
import com.tangym.constant.Const;
import com.tangym.dataparser.ExcelDataParser;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@Api(name = "module1") /* name 对应测试数据表格名 */
public class DemoTest extends BaseCase { /* 测试脚本要继承BaseCase */

    @Test(dataProvider = Const.PROVIDER_DATA, description = "试用测试redis接口") /* description 描述测试case，更多参数发掘查看@Test注解源码 */
    public void tryRedisWithDataProvider(Map<String, String> providerParams) { /* 方法名
        与excel中sheet表名对应 */
        /* 解析测试数据 */
        ExcelDataParser dataParser = new ExcelDataParser();
        dataParser.parseProviderParams(providerParams);

        /* 请求接口 */
        RestAssured.baseURI = "https://try.redis.io"; //因为演示使用了多个不同的baseURI，所以需要重新赋值覆盖env.yml中默认的baseURI
        Response response = ru.httpGet(dataParser.commonParams.get(Const.URL).toString(), dataParser.requestParams);

        /* 结果断言 */
        assertThat("请求返回状态码为200", response.getStatusCode(), equalTo(200));
    }


}
