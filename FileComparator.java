import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Stream;

class FileComparator {

    public static final String folder = "folderPath";
    private static final String placeHolderForIgnorableWhiteSpaces = ",";

    private static String thingsToIgnore = "\\s+";//whitespace

    public static void main(String[] args) throws Exception{

        final Stream<String> streamOfWords1 = Stream.concat(getStreamOfWords("FileOrPage1.txt"),getStreamOfWords("FileOrPage1.txt"));//if required append other files stream as well
        final Stream<String> streamOfWords2 = Stream.concat(getStreamOfWords("FileOrPage2.txt"),getStreamOfWords("FileOrPage2.txt"));

        assertStreamEquals(streamOfWords1, streamOfWords2);

    }

    static void assertStreamEquals(Stream<?> s1, Stream<?> s2) throws Exception {
        Iterator<?> iter1 = s1.iterator(), iter2 = s2.iterator();
        while(iter1.hasNext() && iter2.hasNext()) {
            //System.out.println(iter1.next().equals(iter2.next()));
            if (!iter1.next().equals(iter2.next()))
            {
                throw new Exception("Files are not same");
            }
        }

        if (iter1.hasNext() || iter2.hasNext())
        {
            throw new Exception("Files are not same");
        }

        System.out.println("Hurray!! Files equal");

    }

    public static Stream<String> getStreamOfWords(String fileName) throws IOException {
        final Path f1 = Paths.get(folder+fileName);
        final Stream<String> streamOfLines1 = Files.lines(f1)
                                                    .map(s -> s.replaceAll(thingsToIgnore, placeHolderForIgnorableWhiteSpaces));//it will replace all whitespaces with ","

        return streamOfLines1.flatMap(s -> { return Stream.of(s.split(placeHolderForIgnorableWhiteSpaces)); });
    }
}
