import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckForCorrectDomain {

    private static String readFile(String filename)
    {
        String content = null;
        File file = new File(filename); //for ex foo.txt
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader !=null) try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    private static String[] regexps = new String[]{
            "[A-Z][a-z]{2}\\s[0-9]\\s\\d{2}:\\d{2}:\\d{2}",
            "\\[Login.*?\\]",
            "\\[Data Object.*?\\]",

            //добавить шаблоны
    };

    public static void main(String[] args) {

        String filename = "C:\\Users\\natali\\Desktop\\testSplit.txt";
        String content = readFile(filename);

        String[] piecesOfLog = content.split("(?=(?:[A-Z][a-z]{2}\\s[0-9]\\s\\d{2}:\\d{2}:\\d{2}))");

        for (int i =0; i < piecesOfLog.length; i++){
            String prettyLogString = "";//заводим строчную переменную в которую будем по очереди добавлять кусочки нашей красивой строчки
            String logRow = piecesOfLog[i];//в переменную logRow записываем наш неотформатированный кусок лог файла, т.е. piecesOfLog[i]

            logRow = logRow.replaceAll("\\n"," ");//удаляем(точнее заменяем на пробел) символы \n так как с ними регулярка плохо парсит

            //мы можем выдернуть из неотформатированного куска лога столько "элементов" сколько хотим
            //для этого мы заводим чуть выше массив   regexps    с перечнем шаблонов, по которым мы будем вытаскивать нужную информацию
            //и дальше в цикле начинаем искать В КУСКЕ лога (переменная logRow) маленькие кусочки соответствующие наших шаблончикам(регуляркам)
            for(int j = 0; j < regexps.length; j++){

                Pattern p = Pattern.compile(regexps[j]);//формируем объект класса Паттерн (ему мы указываем какой регулярное выражение мы должны использовать сейчас)
                Matcher m = p.matcher(logRow);//формируем объект класса Матчер

                //метод find объекта класса Матчер показывает нам найдено ли что-то по шаблончику
                boolean isFound = m.find();
                if(isFound){
                    //если да - то добавляем этот кусочек теста к красивой строчке
                    //получается что вначале у нас будет
                    // Jun 9 09:53:06
                    //потом
                    // Jun 9 09:53:06 [Login Username: alan@veridinet.onmicrosoft.com (repository: alan@veridinet.onmicrosoft.com)]
                    //и т. д.
                    prettyLogString = prettyLogString + m.group() + " ";
                }
            }
            //после того как перебрали их(шаблончиики/регулярки) все и подобавляли найденые кусочки в одну строку - можем вывести красивую строчку

            System.out.println(prettyLogString);
            System.out.println();
        }
    }
}