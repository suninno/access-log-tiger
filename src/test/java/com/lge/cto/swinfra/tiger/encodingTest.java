package com.lge.cto.swinfra.tiger;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class encodingTest {

    @Test
    public void testBase64() {
        String based = "L21haW4vZG93bmxvYWQvYXR0YWNobWVudHMvMjEyMDA5Nzc5LzE164WEK3dlYk9TK+qwnOuwnOqygO2GoOuztOqzoOyEnF8yMDE0MDMyNi56aXA=";
        String original = "/main/download/attachments/212009779/15년+webOS+개발검토보고서_20140326.zip";

        System.out.println(Base64.encodeBase64String(StringUtils.getBytesUtf8(original)));

        String decoded = StringUtils.newStringUtf8(Base64.decodeBase64(based));
        System.out.println(decoded);
        assertThat(original, is(decoded));

    }
}
