package lt.metasite.controllers;

import lt.metasite.data.WordCount;
import lt.metasite.data.WordFilter;
import lt.metasite.service.WordsService;
import lt.metasite.threads.WordsCountFileWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class FileUploadController {

    @Resource
    private WordsService wordsService;

    private String path = "uploaded-files/";
    private String displayPath = "src/main/webapp/display/words-interval-";

    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
    public @ResponseBody
    List<WordCount> upload(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
        List<WordCount> wordCounts = new ArrayList<>();
        List<File> convertedFiles = convertFiles(request, path);
        Map<String, Integer> foundWords = wordsService.findWords(convertedFiles);
        writeFilteredWordsToFile(foundWords);

        foundWords
                .entrySet()
                .stream()
                .forEach(map -> wordCounts.add(new WordCount(map.getKey(), map.getValue())));

        return wordCounts;
    }

    private void writeFilteredWordsToFile(Map<String, Integer> foundWords) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        List<WordFilter> filtersData = new ArrayList<>();
        filtersData.add(new WordFilter('A', 'G'));
        filtersData.add(new WordFilter('H', 'N'));
        filtersData.add(new WordFilter('O', 'U'));
        filtersData.add(new WordFilter('V', 'Z'));
        for (WordFilter wordFilter : filtersData) {
            String fileName = displayPath + wordFilter.getFrom() + "-" + wordFilter.getTill() + ".txt";
            WordsCountFileWriter wordsCountFileWriter = new WordsCountFileWriter(fileName, foundWords, wordFilter);
            executorService.submit(wordsCountFileWriter);
        }
    }

    private List<File> convertFiles(MultipartHttpServletRequest request, String path) {
        Iterator<String> itr = request.getFileNames();
        List<File> files = new ArrayList<>();

        while (itr.hasNext()) {
            List<MultipartFile> multipartFiles = request.getFiles(itr.next());
            for (MultipartFile file : multipartFiles) {
                File convertedFile = new File(path, file.getOriginalFilename());
                try {
                    if (convertedFile.exists()) {
                        convertedFile.delete();
                    }
                    convertedFile.createNewFile();
                    FileOutputStream fos = new FileOutputStream(convertedFile);
                    files.add(convertedFile);
                    fos.write(file.getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return files;
    }
}