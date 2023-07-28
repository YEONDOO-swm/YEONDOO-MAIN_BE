package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class utils {
    public static void installFastApi() throws IOException, InterruptedException {
    System.out.println("FastAPI is not installed. Installing...");
    Process process = Runtime.getRuntime().exec(getPipCommand() + " install fastapi");
    process.waitFor();
    System.out.println("FastAPI installed successfully!");
    System.out.println("uvicorn is Installing...");
    Process process2 = Runtime.getRuntime().exec(getPipCommand() + " install uvicorn");
    process2.waitFor();
    System.out.println("uvicorn installed successfully!");
}

    private static String getPipCommand() {
        // 운영체제에 따라 pip 또는 pip3를 선택하여 실행하는 명령어 반환
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            return "pip"; // 윈도우에서는 pip를 사용
        } else {
            return "pip3"; // 유닉스 계열(맥, 리눅스 등)에서는 pip3를 사용
        }
    }

    public static boolean isFastApiInstalled() throws IOException, InterruptedException {
        // "fastapi"를 설치한 경우에는 정상적으로 실행되고, 그렇지 않은 경우 오류가 발생합니다.
        Process process = Runtime.getRuntime().exec(getPipCommand()+" show fastapi");
        process.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("Name: fastapi")) {
                return true;
            }
        }
        return false;
    }
}
