package com.example.teachermanagement.service;

import com.example.teachermanagement.model.Appointment;
import com.example.teachermanagement.model.Teacher;
import com.example.teachermanagement.repository.AppointmentRepository;
import jakarta.persistence.Id;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class TeacherScraperService {
        private final AppointmentRepository appointmentRepository;

    public TeacherScraperService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Teacher scrapeTeacherInfo(String chineseName) throws IOException {
        String pinyinName = convertToPinyin(chineseName);
        String url = "https://homepage.hit.edu.cn/" + pinyinName + "?lang=zh";
        Document doc = Jsoup.connect(url).get();

        // 获取主页URL
        String homepageUrl = doc.select("div.row2 .d2 .web").attr("href");
        if (!homepageUrl.startsWith("http")) {
            homepageUrl = "https://homepage.hit.edu.cn/" + homepageUrl;
        }
        doc = Jsoup.connect(homepageUrl).get(); // 访问正确的主页URL

        String name = doc.select("h3.chineseName").text();
        String college = doc.select("span.user-position").attr("title");
        String title = doc.select("em.user-post").text();
        String phone = doc.select("li:contains(电话) p").text();

        // 修正邮箱顺序
        String email = doc.select("li:contains(邮箱) p.EmailText").text();
        email = new StringBuilder(email).reverse().toString().replace("ude.tih@", "@hit.edu.cn");

        String address = doc.select("li:contains(地址) p").text();
        String researchDirection = doc.select("li:contains(研究方向) span.user-label").text(); // 获取研究方向

        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setCollege(college);
        teacher.setTitle(title);
        teacher.setPhone(phone);
        teacher.setEmail(email);
        teacher.setAddress(address);
        teacher.setHomepageUrl(homepageUrl);
        teacher.setResearchDirection(researchDirection); // 设置研究方向



        return teacher;
    }

    private String convertToPinyin(String chinese) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        StringBuilder pinyin = new StringBuilder();

        for (char c : chinese.toCharArray()) {
            try {
                if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
                    pinyin.append(PinyinHelper.toHanyuPinyinStringArray(c, format)[0]);
                } else {
                    pinyin.append(c);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
        }
        return pinyin.toString();
    }
}
