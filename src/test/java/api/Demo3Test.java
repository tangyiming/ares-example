package api;

import api.common.ZentaoLogin;
import com.tangym.BaseCase;
import com.tangym.annotations.Api;
import com.tangym.constant.Const;
import com.tangym.dataparser.EnvYamlParser;
import com.tangym.dataparser.ExcelDataParser;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

/**
 * 实现登录抽象接口，并完成登录获取cookies，进行用户信息查询
 */

@Api(name = "禅道") //excel文件名可以是中文
public class Demo3Test extends BaseCase {

    private final Cookies cookies;

    //在该类构造函数中进行登录等前置工作
    public Demo3Test() {
        /* 样板代码：读取配置登录信息 */
        String url = EnvYamlParser.getProperty("baseURI") + EnvYamlParser.getProperty("loginURL");
        String account = EnvYamlParser.getProperty("loginAccount");
        String password = EnvYamlParser.getProperty("loginPassword");

        /* 进行登录 */
        ZentaoLogin zentaoLogin = new ZentaoLogin();
        cookies = zentaoLogin.getLoginCookies(url, account, password);

    }

    @Test(dataProvider = Const.PROVIDER_DATA, description = "登录禅道并查询用户信息")
    public void loginAndQueryUserInfo(Map<String, String> providerParams) {
        /* 解析测试数据 */
        ExcelDataParser dataParser = new ExcelDataParser();
        dataParser.parseProviderParams(providerParams);

        /* 请求接口 */
        Response response = ru.httpGet(dataParser.commonParams.get(Const.URL).toString(), cookies);

        /* 结果断言 */
        assertThat(response.getBody().asString(), containsString((String) dataParser.commonParams.get(Const.EXCEPT_RESULT)));
    }
}
