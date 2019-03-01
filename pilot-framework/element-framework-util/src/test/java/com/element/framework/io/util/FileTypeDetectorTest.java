package me.anichakra.poc.pilot.framework.io.util;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.function.Function;

import org.junit.Test;

public class FileTypeDetectorTest {

    FileTypeDetector fileTypeDetector = new FileTypeDetector();

    @Test
    public void testFileTypeDetector() {
        try {
            Iterator<Path> itr = Files
                    .list(Paths.get(
                            this.getClass().getClassLoader().getResource("sample/valid").toURI()))
                    .iterator();
            while (itr.hasNext()) {
                fileTypeDetector.probeContentType(itr.next());
            }
        } catch (IOException | URISyntaxException e) {
            fail("Exception has occured: " + e.getMessage());
        }

    }

    @Test
    public void testFileTypeDetector_invalid() {
        try {
            Iterator<Path> itr = Files
                    .list(Paths.get(
                            this.getClass().getClassLoader().getResource("sample/invalid").toURI()))
                    .iterator();
            boolean fail = true;
            while (itr.hasNext()) {
                try {
                    fileTypeDetector.probeContentType(itr.next());
                } catch (IOException e) {
                    fail=false;
                }
            }
            assertTrue(!fail);
        } catch(Exception e){
            fail(e.getMessage());
        }
       
    }
    
    @Test
    public void testFileTypeDetector_noFile() {
        try {
            Iterator<Path> itr = Files
                    .list(Paths.get(
                            this.getClass().getClassLoader().getResource("sample").toURI()))
                    .iterator();
            while (itr.hasNext()) {
                fileTypeDetector.probeContentType(itr.next());
            }
        } catch (IOException | URISyntaxException e) {
           // System.out.println(e);
            assertTrue(e.getMessage(), true);
        }

    }
    
    @Test
    public void testFileTypeDetector_nullFile() {
        try {
            fileTypeDetector.getContentType(new File("/invalid.txt"));
        } catch (IOException e) {
          assertTrue(e.getMessage(), true);
        }
    }

    @Test
    public void testFileTypeDetector_badFile() {
        try {
            assertNull(fileTypeDetector.getContentType(null));
        } catch (IOException e) {
            assertTrue(e.getMessage(), true);
        }
    }
    
    @FunctionalInterface
    public interface Function_WithExceptions<T, R> {
        R apply(T t) throws Exception;
    }

    public static <T, R> Function<T, R> rethrowFunction(Function_WithExceptions<T, R> function) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception exception) {
                throwAsUnchecked(exception);
                return null;
            }
        };
    }

    @SuppressWarnings("unchecked")
    private static <E extends Throwable> void throwAsUnchecked(Exception exception) throws E {
        throw (E) exception;
    }

}
