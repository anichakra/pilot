package me.anichakra.poc.pilot.framework.io.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is a custom FileTypeDetector that detects the type of file based on the first 16 bytes of
 * the file content. It first checks the extension of the file and matches that with the file
 * content. If match is not found or the extension of the file is not recognizable then
 * {@link IOException} is thrown.
 * 
 * @author Anirban C1
 *
 */
public class FileTypeDetector extends java.nio.file.spi.FileTypeDetector {
    private static final Logger LOGGER = LogManager.getLogger();

    public FileTypeDetector() {
        super();
    }

    static final class Header implements Serializable{
        private byte[] header = null;

        Header(byte[] header) {
            this.header = header;
        }

        @Override
        public String toString() {
            return Arrays.toString(header);
        }
    }

    /**
     * Following are the different types of file whose content can be checked.
     * 
     * @author Anirban C1
     *
     */
    public static enum ContentType {
        /**
         * 
         */
        TXT("text/html", h -> txt(h)),
        /**
         * 
         */
        HTML("text/html", h -> (html(h))),
        /**
         * 
         */
        XML("application/xml", h -> (xml(h))),
        /**
         * 
         */
        PDF("application/pdf", h -> (pdf(h))),
        /**
         * 
         */
        DOC("application/word", h -> (doc(h))),
        /**
         * 
         */
        DOCX("application/msword", h -> (doc(h))),
        /**
         * 
         */
        XLS("application/excel", h -> xls(h)),
        /**
         * 
         */
        XLSX("application/msexcel", h -> xlsx(h)),
        /**
         * 
         */
        CSV("text/html", h -> txt(h)),
        /**
         * 
         */
        GIF("image/gif", h -> (gif(h))),
        /**
         * 
         */
        JPG("image/jpg", h -> (jpg(h))),
        /**
         * 
         */
        BMP("image/x-bitmap", h -> (bmp(h))),
        /**
         * 
         */
        TIF("image/tiff", h -> (tif(h))),
        /**
         * 
         */
        PNG("image/png", h -> (png(h))),
        /**
         * 
         */
        ZIP("application/zip", h -> (zip(h)));

        String mime;
        Predicate<Header> predicate;

        ContentType(String mime, Predicate<Header> predicate) {
            this.mime = mime;
            this.predicate = predicate;
        }

        static boolean txt(Header h) {
            return StringUtils.isAsciiPrintable(new String(h.header));
        }

        static boolean html(Header h) {
            String val = new String(h.header).toUpperCase();
            return (val.trim().startsWith("<!DOCTYPE H"));
        }

        static boolean xml(Header h) {
            String val = new String(h.header).toLowerCase();
            return ((val.trim().startsWith("<?xml vers")));
        }

        static boolean pdf(Header h) {
            return match(h, new int[] { 0x25, 0x50, 0x44, 0x46, 0x2d, 0x31, 0x2e });
        }

        static boolean doc(Header h) {
            int[] m1 = { 0xD0, 0xCF, 0x11, 0xE0, 0xA1, 0xB1, 0x1A, 0xE1 };
            int[] m2 = { 80, 75, 3, 4, 20, 0, 6, 0 };
            return (match(h, m1) || match(h, m2));
        }

        static boolean xls(Header h) {
            return match(h, new int[] { 208, 207, 17, 224, 161, 177, 26, 225 });
        }

        static boolean xlsx(Header h) {
            return match(h, new int[] { 80, 75, 3, 4, 20, 0, 6, 0, 8, 0, 0 });
        }

        static boolean gif(Header h) {
            return match(h, new int[] { 'G', 'I', 'F', '8' });
        }

        static boolean jpg(Header h) {
            return match(h, new int[] { 255, 216, 255, 224, 0, 16, 74, 70, 73, 70, 0 });
            // || (h.c1 == 0xFF && h.c2 == 0xD8 && h.c3 == 0xFF)
            // && ((h.c4 == 0xE0) || ((h.c4 == 0xE1) && (h.c7 == 'E' && h.c8 == 'x'
            // && h.c9 == 'i' && h.c10 == 'f' && h.c11 == 0)));

        }

        static boolean bmp(Header h) {
            return match(h, new int[] { 66, 77, 246, 108 });
        }

        static boolean tif(Header h) {
            return match(h, new int[] { 0x49, 0x49, 0x2a, 0x00 });
        }

        static boolean png(Header h) {
            return match(h, new int[] { 137, 80, 78, 71, 13, 10, 26, 10 });
        }

        static boolean zip(Header h) {
            return match(h, new int[] { 'P', 'K' });
        }

        Predicate<Header> getPredicate() {
            return predicate;
        }

        String getMime() {
            return mime;
        }

        private static boolean match(Header h, int[] ms) {
            for (int i = 0; i < ms.length; i++) {
                if ((h.header[i] & 0xff) != ms[i])
                    return false;
            }
            return true;
        }

    }

    /**
     * The ContentType is determined based on first few byte content of the file
     * 
     * @param file
     *            The file whose content will be validated, based on the extension of the file
     * @return The ContentType if File is invalid, content is too short (less than 16 byte),
     *         extension is missing and content is not matching with the extension.
     * @throws IOException
     */
    public ContentType getContentType(final File file) throws IOException {
        if (file == null || !file.exists() ||!file.isFile()) {
            throw new IOException(file + ":file is invalid");
        }
        final byte[] imageData = new byte[16];
        try (InputStream is = new FileInputStream(file);) {
            if (is.read(imageData) != imageData.length) {
                throw new IOException(file + ":content is too short");
            }
        }
        final byte[] h = new byte[11];
        System.arraycopy(imageData, 0, h, 0, Math.min(imageData.length, h.length));
        Header header = new Header(h);
        LOGGER.debug("Content {} for file {}", header, file);
        String filename = file.getName();
        String[] extns = filename.split("\\.");
        if (extns.length == 1)
            throw new IOException(file + ":no extension provided");

        String extension = extns[extns.length - 1];
        for (ContentType ct : ContentType.values()) {
            if (ct.name().equalsIgnoreCase(extension) && ct.getPredicate().test(header)) {
                return ct;
            }
        }
        throw new IOException(file + ":No match found with header data:" + header);
    }

    @Override
    public String probeContentType(Path path) throws IOException {
        return getContentType(path.toFile()).getMime();
    }

}
