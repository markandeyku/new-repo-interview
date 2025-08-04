package dsignpattern.structural;

public class FacadeDesignPattern {
}

/**
 *  Real-World Example – Video Conversion System
 * Scenario: Converting a video file (e.g., .mp4 to .avi). The underlying system requires multiple complex steps:
 *
 * Reading the video file
 * Extracting audio
 * Compressing the video
 * Encoding into the target format
 * Instead of calling each step manually, we'll create a Facade to simplify the process.
 */

// Subsystem 1: Video File Reader
class VideoFile {
    private String fileName;

    public VideoFile(String fileName) {
        this.fileName = fileName;
    }

    public void read() {
        System.out.println("Reading video file: " + fileName);
    }
}

// Subsystem 2: Codec Factory
class CodecFactory {
    public void extractCodec(String fileName) {
        System.out.println("Extracting codec for file: " + fileName);
    }
}

// Subsystem 3: Audio Mixer
class AudioMixer {
    public void mixAudio(String fileName) {
        System.out.println("Mixing audio for: " + fileName);
    }
}

// Subsystem 4: Video Compressor
class VideoCompressor {
    public void compress(String fileName) {
        System.out.println("Compressing video file: " + fileName);
    }
}




// Facade Class,   Facade Class (Simplified Interface)
class VideoConversionFacade {
    public void convertVideo(String fileName, String format) {
        System.out.println("Starting video conversion...");

        // Delegating complex logic to respective subsystems
        VideoFile videoFile = new VideoFile(fileName);
        videoFile.read();

        CodecFactory codecFactory = new CodecFactory();
        codecFactory.extractCodec(fileName);

        AudioMixer audioMixer = new AudioMixer();
        audioMixer.mixAudio(fileName);

        VideoCompressor compressor = new VideoCompressor();
        compressor.compress(fileName);

        System.out.println("Conversion to " + format + " completed successfully! 🎬");
    }
}


// client code

 class FacadePatternDemo {
    public static void main(String[] args) {
        // The client only interacts with the Facade
        VideoConversionFacade facade = new VideoConversionFacade();
        facade.convertVideo("my_movie.mp4", "avi");
    }
}

/**
 * Where to Use Facade Design Pattern
 * Complex Systems — Libraries with complex APIs (e.g., video processing, payment gateways).
 * Legacy Code Integration — Wrap outdated systems with a modern interface.
 * Building Service Layers — Facades simplify access to multiple microservices.
 * API Gateways in Microservices — Acts as a single entry point for multiple backend services.
 */

