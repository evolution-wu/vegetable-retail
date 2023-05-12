package com.spring.afterend.config;

import cn.hutool.json.JSONUtil;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
@ConfigurationProperties(prefix = "alipay")
public class AliPayConfig {
    private String appId;
    private String appPrivateKey;
    private String alipayPublicKey;
    private String notifyUrl;


    @PostConstruct
    public void init() {
        // 设置参数（全局只需设置一次）
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi.alipaydev.com";
        config.signType = "RSA2";
        //config.appId = this.appId;
        config.appId = "2021000122688787";
        //不知道 为啥yml配置的无效。。。。
        //config.merchantPrivateKey = this.appPrivateKey;
        config.merchantPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC2gaXxKSo+wc6jE0buMMIVcu/M2p2n6mRrVqHpBzbDPdRZC+Uq1QQ+VV6VmoYvIyl6//ohGfwyBJ13uIyV/n1PT2bT8V+6dXB7Qnmg6Nt/aqXLxh6KgLc6aLIzrJPsvgXHxhQDcTtXyMcGESfslSUAVk9zCE4ovaNkWpocjnmWhpcMSHVAyq+cPXXe7dycNReUzaMIdc4nKuXyiUfeene5YMRaJBmeOQDtUvk6hvpe/0T6qGLIshjVD+tAQTmCYXtujPypDJrbWuDoc19icpSezF+f3LSnpX5zNXpFsGon0yieUkKqNLgbIa/SVBZayj3yFtwrQrVAEQ0Mp5JqUzwvAgMBAAECggEBAIq/D4fCctJZBp3lRtRIDxS9V17WiGqigAs2Rkrg6BoQBEJaD7O7U8mLnyd+3SoywhM3TVIi7h6ccZYVoBNb8Kn9xD+2Tb90Dro2ND9HJ6+7RSYgjRFgf2uRMoCkX6ToZX6VsJtZVvMKuXt8SWzLieCyvR3Hn5vLZ88gLe8txXPPvvfC9uMVoHDr2MEFBRQnN9ZMVi8gCrD7zulJZ2iQ/VYBTXDfHWly91f3e98NPsXNrorPPdbKeggWpV6ORwrbXyN7QjqAMyl9QRoQJ1zhoHWKkefeWbzQ1IwrAjDB5T/6zedkZUul7xMVpXGIHFCff4Y6L78mqIAedU1vMfcyuLECgYEA5ehMou1lsnHrwGQa6kts57R81eD1vyMfeqHOjAP2ZSZmqJvPrFrxJg4t+zIowW5elYaoRbI31UE4ErPdjy8XYgrW71PDy6CHhJrQdoOEQ6zG88tmwX3ToC64aIyb3AKozzRsqscB1l3xaYj6MsuZyXngcFr1Wy3mQYy3FG2R4ScCgYEAyzgqtZkPgI5njEG8GLUMDWGDtpXCbKi/J1I6aLplIYF3x/LxJoB9b+PHsFM5ydYt2FXN7kS5jU1vqrBjqDkfqGLUmaCjjolUcwsTL+qYO/8Cp5HEBtOL83ZrKnrfLgFSNkHVNZAY/zy9i93x2LQmNBmJrORqhJsleDck5k0CobkCgYB89PlSRH1Vs51TABWw6jXS6cI/7PkkXc6qNG+yoiS1bV22npkNMJCJAoPzV5vXJkm6M2oaF3dJLR1w7NmrDQlEfGe9xGVSs3l0lUBgabLlpW6s6wx6dg0jrsED8gh0V+2wUOoPGALjpEOl/JEgMpDvdJKL/qQEXrobPoPzUmpLWQKBgHOT0S6RC7EIzLmHVUwUA/PERx3b1MTpwDXD3XZctFG3CYHNmPtVzoGJYbPV0aMKTe6g9ksSCRnlNaI1SaiOPIQPGWz6YKsbTc1UOihhWw2cPk1BaOSdAhaIffCeCzpmA/TZwkIBX0YirIbUxsyBwMyWzadoDXC3sDrL2g+lMt75AoGBAMC8QPIlNor9gh6vgOGTTtBc8imr+RXfKtmiRwKPYDG3YVMj8BUY/r48yBY3yt+IBPNpeD8uEaPhwhimcbuIGREPhlo2liYr7uAtTaGdEgf15OH/+xX8zH4P7dMhzlG/zuHdNS5Z1Bdlg83Jlto8+4IC3QEOB0n4f9WjbN4UTS++";
        config.alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhythe7m+nkgWmB3W/SynlJhQhesg1HOKbVQoDoXVzL//GZvEQ5UCGdmTGOHKSihFKue8xpFUekz9YJ+KPjA+/Qy+L97HSwU6Qt0Vo82W7zQ/LxgfWRQ8v0v47ttfpGSa8bib6fQPdBgp7ggWRsC6IgXwnTSsjHICDpyWP7VD8rniDyDSiKwWFhPQli02ZUdNz/aAc6+KR/MfRcoisNW16DyGQYApkdctaFuQ2x0tCA/PSMfazeh/IkHXxxKBugN7J+aIqQ+MIdqtOL/izlur+AS0Vmbuzd/LYG35jt8HKRaXkeZa1AgN15VOjZ4fv18AL9XqMNbBXWj7V0FeHjdwUQIDAQAB";
        //config.alipayPublicKey = this.alipayPublicKey;
        //config.notifyUrl = this.notifyUrl;
        config.notifyUrl = "http://59g8x3.natappfree.cc/alipay/notify";
        Factory.setOptions(config);
        System.out.println(JSONUtil.toJsonStr(config));
        System.out.println("=======支付宝SDK初始化成功=======");
    }

}
