import java.io.*;
import java.util.*;

public class FileWorker{

    private static String text = "This new text \nThis new text2\nThis new text3\nThis new text4\n";
    private static String fileName = "2.txt";

    public static void main(String[] args) throws FileNotFoundException{
        //Scanner scan = new Scanner(System.in);
        //System.out.println();

        //Запись в файл
        //FileWorker.write(fileName, text);

        //Чтение файла
        String textFromFile = FileWorker.read(fileName);
        System.out.println(textFromFile);

    }
    public static void write(String fileName,String text) {
        //Определяем файл
        File file = new File(fileName);

        try {
            //проверяем, что если файл не существует то создаем его
            if(!file.exists()){
                file.createNewFile();
            }

            //PrintWriter обеспечит возможности записи в файл
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());

            try {
                //Записываем текст у файл
                out.print(text);
            } finally {
                //После чего мы должны закрыть файл
                //Иначе файл не запишется
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String read(String fileName) throws FileNotFoundException {
        //Этот спец. объект для построения строки
        StringBuilder sb = new StringBuilder();

        exists(fileName);

        try {
            //Объект для чтения файла в буфер
            BufferedReader in = new BufferedReader(new FileReader(fileName));//file.getAbsoluteFile()
            try {
                int numberOfLines = 0;
                int numberOfWords=0;
                Map<String, Integer> hashMap = new HashMap<String, Integer>();

                //В цикле построчно считываем файл
                String s;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                    numberOfLines++;
                    String[] WordsInLine;
                    WordsInLine = s.replaceAll("[.,]", " ").split("\\s+");
                    for(int i=0;i<WordsInLine.length;i++){

                                if(hashMap.containsKey(WordsInLine[i].toLowerCase())) {
                                    hashMap.put(WordsInLine[i].toLowerCase(), hashMap.get(WordsInLine[i].toLowerCase()) + 1);

                               }
                               else {
                                    hashMap.put(WordsInLine[i].toLowerCase(),1);
                                }
                    }
                    numberOfWords += WordsInLine.length;
                                    }

                System.out.print("I've found ");
                System.out.print(numberOfLines);
                System.out.println(" lines");

                System.out.print("I've found ");
                System.out.print(numberOfWords);
                System.out.println(" words");

                // Получаем набор элементов
                Set<Map.Entry<String, Integer>> set = hashMap.entrySet();

// Отобразим набор
                for (Map.Entry<String, Integer> me : set) {
                    System.out.print(me.getKey() + ": ");
                    System.out.println(me.getValue());
                }

                System.out.println();
                System.out.println();
                System.out.println();

                final Map<String, Integer> sortedMap = new TreeMap<>(new Comparator<String>() {
                    @Override
                    public int compare(String lhs, String rhs) {
                        int result = hashMap.get(lhs).compareTo(hashMap.get(rhs));
                        return result == 0 ? -1 : result;
                    }
                });
                sortedMap.putAll(hashMap);

                for (final Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
                    System.out.println(String.format("%s = %s", entry.getKey(), entry.getValue()));
                }
            } finally {
                //Также не забываем закрыть файл
                in.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        //Возвращаем полученный текст с файла
        return sb.toString();
    }

    private static void exists(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.exists()){
            throw new FileNotFoundException(file.getName());
        }
    }






}