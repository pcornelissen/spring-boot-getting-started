package com.packtpub.yummy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoControllerRestTemplateTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void sayTheTime() throws Exception {
        String result1 = restTemplate.getForObject("/",String.class);
        String result2 = restTemplate.getForObject("/",String.class);
        assertNotEquals(result1, result2);
    }
    @Test
    public void sayTheTimeMany() throws Exception {
        String result = restTemplate.getForObject("/many?name=Sansa&repetitions=3",String.class);

        assertThat(result, containsString("Sansa"));
        assertThat(result, containsString("3. "));
        assertThat(result, not(containsString("4. ")));
    }

    @Test
    public void sayTheTimeManyParams() throws Exception {
        String result = restTemplate.getForObject("/manyParams?name=Sansa&repetitions=3",String.class);

        assertThat(result, containsString("Sansa"));
        assertThat(result, containsString("3. "));
        assertThat(result, not(containsString("4. ")));
    }

    @Test
    public void sayTheTimeManyParamsPath() throws Exception {
        String result = restTemplate.getForObject("/many/John/5?name=Sansa&repetitions=3",String.class);

        assertThat(result, containsString("Sansa"));
        assertThat(result, containsString("3. "));
        assertThat(result, not(containsString("4. ")));
    }
    @Test
    public void sayTheTimeManyParamsPathFromPath() throws Exception {
        String result = restTemplate.getForObject(
                "/many/{name}/{repetitions}",
                String.class,
                "John",5
                );

        assertThat(result, containsString("John"));
        assertThat(result, containsString("5. "));
        assertThat(result, not(containsString("6. ")));
    }

    @Test
    public void sayTheTimeManyPathExplicit() throws Exception {
    }

    @Test
    public void sayTheTimeManyParamsPost() throws Exception {
        String result = restTemplate.postForObject("/manyParams",
                new DemoController.Params("John",5),
                String.class);

        assertThat(result, containsString("John"));
        assertThat(result, containsString("5. "));
        assertThat(result, not(containsString("6. ")));
    }
}