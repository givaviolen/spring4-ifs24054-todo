package org.delcom.starter.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@RestController
public class HomeController {

    @GetMapping("/")
    public String hello() {
        return "Hay Abdullah, selamat datang di pengembangan aplikasi dengan Spring Boot!";
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam String nama) {
        return "Hello, " + nama + "!";
    }

    @GetMapping("/informasi-nim")
    public String informasiNim(@RequestParam String nim) {
        // Validasi panjang NIM
        if (nim.length() != 8) {
            return "NIM harus 8 karakter";
        }

        // Ekstrak komponen NIM
        String kodeProdi2Digit = nim.substring(0, 2);
        String kodeProdi3Digit = nim.substring(0, 3);
        String jenisProgram = nim.substring(2, 3);
        String angkatan = nim.substring(3, 5);
        String urutan = nim.substring(5, 8);

        // Mapping kode program studi - prioritaskan 3 digit terlebih dahulu
        Map<String, String> programStudi = new HashMap<>();
        programStudi.put("114", "Diploma 4 Teknologi Rekasaya Perangkat Lunak");
        programStudi.put("113", "Diploma 3 Teknologi Informasi");
        programStudi.put("133", "Diploma 3 Teknologi Komputer");
        programStudi.put("11", "Sarjana Informatika");
        programStudi.put("12", "Sarjana Sistem Informasi");
        programStudi.put("14", "Sarjana Teknik Elektro");
        programStudi.put("21", "Sarjana Manajemen Rekayasa");
        programStudi.put("22", "Sarjana Teknik Metalurgi");
        programStudi.put("31", "Sarjana Teknik Bioproses");

        // Cari program studi - prioritaskan 3 digit
        String namaProdi = programStudi.get(kodeProdi3Digit);
        if (namaProdi == null) {
            namaProdi = programStudi.get(kodeProdi2Digit);
        }

        if (namaProdi == null) {
            return "Program Studi tidak Tersedia";
        }

        return "Inforamsi NIM " + nim + ": >> Program Studi: " + namaProdi + 
               ">> Angkatan: 20" + angkatan + ">> Urutan: " + Integer.parseInt(urutan);
    }

    @GetMapping("/perbedaan-l")
    public String perbedaanL(@RequestParam String data) {
        try {
            // Decode base64
            byte[] decodedBytes = Base64.getDecoder().decode(data);
            String decodedString = new String(decodedBytes);
            
            Scanner scanner = new Scanner(decodedString);
            int n = scanner.nextInt();
            
            // Baca matriks
            int[][] matrix = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = scanner.nextInt();
                }
            }
            scanner.close();

            // Hitung nilai L dan kebalikan L hanya untuk matriks 3x3 atau lebih besar
            int nilaiL = 0;
            int nilaiKebalikanL = 0;
            int nilaiTengah = 0;
            
            if (n >= 3) {
                // Nilai L (bentuk L: kolom pertama + baris terakhir tanpa elemen pertama)
                for (int i = 0; i < n; i++) {
                    nilaiL += matrix[i][0]; // kolom pertama
                }
                for (int j = 1; j < n; j++) {
                    nilaiL += matrix[n-1][j]; // baris terakhir (kecuali elemen pertama)
                }
                
                // Nilai kebalikan L (bentuk L terbalik: kolom terakhir + baris terakhir tanpa elemen terakhir)
                for (int i = 0; i < n; i++) {
                    nilaiKebalikanL += matrix[i][n-1]; // kolom terakhir
                }
                for (int j = 0; j < n-1; j++) {
                    nilaiKebalikanL += matrix[n-1][j]; // baris terakhir (kecuali elemen terakhir)
                }
                
                // Nilai tengah (elemen tengah matriks)
                if (n % 2 == 1) {
                    nilaiTengah = matrix[n/2][n/2];
                } else {
                    // Untuk matriks genap, ambil rata-rata 4 elemen tengah
                    int mid = n / 2;
                    nilaiTengah = (matrix[mid-1][mid-1] + matrix[mid-1][mid] + 
                                 matrix[mid][mid-1] + matrix[mid][mid]) / 4;
                }
            } else {
                // Untuk matriks 1x1 atau 2x2
                int total = 0;
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        total += matrix[i][j];
                    }
                }
                nilaiTengah = total;
                nilaiL = 0;
                nilaiKebalikanL = 0;
            }

            // Hitung perbedaan
            int perbedaan = Math.abs(nilaiL - nilaiKebalikanL);
            
            // Tentukan nilai dominan
            int dominan = nilaiTengah;
            if (n >= 3) {
                if (nilaiL >= nilaiKebalikanL && nilaiL >= nilaiTengah) {
                    dominan = nilaiL;
                } else if (nilaiKebalikanL >= nilaiL && nilaiKebalikanL >= nilaiTengah) {
                    dominan = nilaiKebalikanL;
                }
            }

            // Format output - sesuai dengan test case (tanpa <br/> di akhir)
            StringBuilder result = new StringBuilder();
            result.append("Nilai L: ").append(nilaiL > 0 ? nilaiL : "Tidak Ada").append("<br/>");
            result.append("Nilai Kebalikan L: ").append(nilaiKebalikanL > 0 ? nilaiKebalikanL : "Tidak Ada").append("<br/>");
            result.append("Nilai Tengah: ").append(nilaiTengah).append("<br/>");
            result.append("Perbedaan: ").append(perbedaan > 0 ? perbedaan : "Tidak Ada").append("<br/>");
            result.append("Dominan: ").append(dominan);
            
            return result.toString();
            
        } catch (Exception e) {
            return "Error processing matrix data";
        }
    }

    @GetMapping("/paling-ter")
    public String palingTer(@RequestParam String data) {
        try {
            // Decode base64
            byte[] decodedBytes = Base64.getDecoder().decode(data);
            String decodedString = new String(decodedBytes);
            
            Scanner scanner = new Scanner(decodedString);
            Map<Integer, Integer> counter = new HashMap<>();
            
            // Baca angka sampai menemukan "---"
            while (scanner.hasNext()) {
                if (scanner.hasNext("---")) {
                    scanner.next();
                    break;
                }
                if (scanner.hasNextInt()) {
                    int num = scanner.nextInt();
                    counter.put(num, counter.getOrDefault(num, 0) + 1);
                } else {
                    scanner.next();
                }
            }
            scanner.close();

            // Validasi counter
            if (counter.isEmpty()) {
                return "Informasi tidak tersedia";
            }

            // Temukan nilai tertinggi, terendah, terbanyak, dan tersedikit
            int tertinggi = Integer.MIN_VALUE;
            int terendah = Integer.MAX_VALUE;
            int maxCount = 0;
            int minCount = Integer.MAX_VALUE;
            int nilaiTerbanyak = 0;
            int nilaiTersedikit = 0;

            for (Map.Entry<Integer, Integer> entry : counter.entrySet()) {
                int num = entry.getKey();
                int count = entry.getValue();

                // Update tertinggi dan terendah
                if (num > tertinggi) tertinggi = num;
                if (num < terendah) terendah = num;

                // Update terbanyak (prioritaskan nilai yang lebih besar jika count sama)
                if (count > maxCount || (count == maxCount && num > nilaiTerbanyak)) {
                    maxCount = count;
                    nilaiTerbanyak = num;
                }

                // Update tersedikit (prioritaskan nilai yang lebih kecil jika count sama)
                if (count < minCount || (count == minCount && num < nilaiTersedikit)) {
                    minCount = count;
                    nilaiTersedikit = num;
                }
            }

            // Hitung jumlah tertinggi dan terendah - SESUAI TEST CASE
            // Jumlah Tertinggi = nilai terbanyak * frekuensinya
            // Jumlah Terendah = nilai tersedikit * frekuensinya
            int jumlahTertinggi = nilaiTerbanyak * maxCount;
            int jumlahTerendah = nilaiTersedikit * minCount;

            // Format output - sesuai dengan test case (tanpa <br/> di akhir)
            StringBuilder result = new StringBuilder();
            result.append("Tertinggi: ").append(tertinggi).append("<br/>");
            result.append("Terendah: ").append(terendah).append("<br/>");
            result.append("Terbanyak: ").append(nilaiTerbanyak).append(" (").append(maxCount).append("x)").append("<br/>");
            result.append("Tersedikit: ").append(nilaiTersedikit).append(" (").append(minCount).append("x)").append("<br/>");
            result.append("Jumlah Tertinggi: ").append(nilaiTerbanyak).append(" * ").append(maxCount).append(" = ").append(jumlahTertinggi).append("<br/>");
            result.append("Jumlah Terendah: ").append(nilaiTersedikit).append(" * ").append(minCount).append(" = ").append(jumlahTerendah);
            
            return result.toString();
            
        } catch (Exception e) {
            return "Error processing data";
        }
    }
}