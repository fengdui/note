package net.smgui.service;

import net.smgui.entity.Problem;
import net.smgui.util.RegUtil;
import org.springframework.stereotype.Service;

@Service
public class TojCrawler extends Crawler {

    @Override
    protected String getProblemUrl(String problemId) {
        return "http://acm.tzc.edu.cn/acmhome/problemdetail.do?&method=showdetail&id="+problemId;
    }

    @Override
    protected void populateProblemInfo(Problem info, String problemId, String html) throws Exception {
//        System.out.println("进入正则");
        info.title = RegUtil.regFind(html, "<h2><strong>([\\s\\S]*?)</strong>").trim();
        info.timeLimit = (Integer.parseInt(RegUtil.regFind(html, "/(\\d*)MS")));
        info.memoryLimit = (Integer.parseInt(RegUtil.regFind(html, "(\\d*)KByte")));
        info.description = (RegUtil.regFind(html, "Times New Roman\">([\\s\\S]*?)</font></p></div>"));
        info.input = (RegUtil.regFind2(html, "Times New Roman\">([\\s\\S]*?)</font></p></div>"));
        info.output = (RegUtil.regFind3(html, "Times New Roman\">([\\s\\S]*?)</font></p></div>"));
        info.sampleInput = (RegUtil.regFind(html, "sample_input\" class=\"sample_input_output\" readonly>([\\s\\S]*?)</textarea></p>"));
        info.sampleOutput = (RegUtil.regFind(html, "sample_output\" class=\"sample_input_output\" readonly>([\\s\\S]*?)</textarea></p>"));
//        System.out.println(info.title);
//        System.out.println(info.timeLimit);
//        System.out.println(info.memoryLimit);
//        System.out.println(info.description);
//        System.out.println(info.input);
//        System.out.println(info.output);
//        System.out.println(info.sampleInput);
//        System.out.println(info.sampleOutput);
    }

    public static void main(String[] args) {
        TojCrawler tojCrawler = new TojCrawler();
        try {
            tojCrawler.crawl("4002");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
