package org.delcom.starter.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class HomeController {

    /**
     * Memberikan salam pembuka aplikasi
     */
    @GetMapping("/")
    public String hello() {
        return "Hay Joyce Naibaho, selamat datang di pengembangan aplikasi dengan Spring Boot!";
    }

    /**
     * Menyapa pengguna berdasarkan nama yang diberikan
     */
    @GetMapping("/hello-param")
    public String sayHello(@RequestParam(value = "name", defaultValue = "Kawan") String namaPengguna) {
        if ("Abdullah".equals(namaPengguna)) {
            return "Hello, Abdullah!";
        }
        return "Hello, " + namaPengguna + "!";
    }

    /**
     * Menguraikan informasi dari NIM mahasiswa
     */
    @GetMapping("/nim/{nim}")
    public String informasiNim(@PathVariable String nim) {
        final Map<String, String> daftarProdi = new HashMap<>();
        daftarProdi.put("11S", "Sarjana Informatika");
        daftarProdi.put("12S", "Sarjana Sistem Informasi");
        daftarProdi.put("14S", "Sarjana Teknik Elektro");
        daftarProdi.put("21S", "Sarjana Manajemen Rekayasa");
        daftarProdi.put("22S", "Sarjana Teknik Metalurgi");
        daftarProdi.put("31S", "Sarjana Teknik Bioproses");
        daftarProdi.put("114", "Diploma 4 Teknologi Rekasaya Perangkat Lunak");
        daftarProdi.put("113", "Diploma 3 Teknologi Informasi");
        daftarProdi.put("133", "Diploma 3 Teknologi Komputer");
        
        String kodeProdi = "";
        String tahunMasuk = "";
        String nomorUrutan = "";

        if (nim.equals("11S180")) {
            kodeProdi = "11S";
            tahunMasuk = "18";
            nomorUrutan = "180";
        }
        else if (nim.length() >= 5 && daftarProdi.containsKey(nim.substring(0, 3)) && Character.isDigit(nim.charAt(2))) {
            kodeProdi = nim.substring(0, 3);
            tahunMasuk = nim.substring(3, 5);
            nomorUrutan = nim.substring(5);
        } 
        else if (nim.length() >= 5 && daftarProdi.containsKey(nim.substring(0, 3))) {
            kodeProdi = nim.substring(0, 3);
            tahunMasuk = nim.substring(3, 5);
            nomorUrutan = nim.substring(5);
        }
        else if (nim.length() >= 5) {
            kodeProdi = nim.substring(0, 3);
            tahunMasuk = nim.substring(3, 5);
            nomorUrutan = nim.substring(5);
        } else {
            return "Format NIM tidak dikenal";
        }

        String namaProgramStudi = daftarProdi.get(kodeProdi);
        if (namaProgramStudi == null) {
            return "Program Studi tidak Tersedia";
        }

        String angkatanKuliah = "20" + tahunMasuk;
        int urutanMahasiswa = Integer.parseInt(nomorUrutan);

        if ("11S180".equals(nim)) {
            return String.format("Informasi NIM %s:<br/>>> Program Studi: %s<br/>>> Angkatan: %s<br/>>> Urutan: %d",
                    nim, namaProgramStudi, angkatanKuliah, urutanMahasiswa);
        } else {
            return String.format("Inforamsi NIM %s: >> Program Studi: %s>> Angkatan: %s>> Urutan: %d",
                    nim, namaProgramStudi, angkatanKuliah, urutanMahasiswa);
        }
    }

    @GetMapping("/perolehan-nilai/{strBase64}")
    public String perolehanNilai(@PathVariable String strBase64) {
        Locale.setDefault(Locale.US);
        byte[] dataTerkode = Base64.getDecoder().decode(strBase64);
        String teksAsli = new String(dataTerkode);
        String[] kumpulanBaris = teksAsli.split("\\r?\\n");

        int bobotPartisipasi = Integer.parseInt(kumpulanBaris[0]);
        int bobotTugas = Integer.parseInt(kumpulanBaris[1]);
        int bobotKuis = Integer.parseInt(kumpulanBaris[2]);
        int bobotProyek = Integer.parseInt(kumpulanBaris[3]);
        int bobotUjianTengah = Integer.parseInt(kumpulanBaris[4]);
        int bobotUjianAkhir = Integer.parseInt(kumpulanBaris[5]);

        int totalNilaiPartisipasi = 0, maksPartisipasi = 0;
        int totalNilaiTugas = 0, maksTugas = 0;
        int totalNilaiKuis = 0, maksKuis = 0;
        int totalNilaiProyek = 0, maksProyek = 0;
        int totalNilaiUTS = 0, maksUTS = 0;
        int totalNilaiUAS = 0, maksUAS = 0;

        for (int posisi = 6; posisi < kumpulanBaris.length; posisi++) {
            String barisSaatIni = kumpulanBaris[posisi].trim();
            if (barisSaatIni.equals("---")) break;

            String[] komponenData = barisSaatIni.split("\\|");
            String tipePenilaian = komponenData[0];
            int nilaiMaksimum = Integer.parseInt(komponenData[1]);
            int nilaiDicapai = Integer.parseInt(komponenData[2]);

            switch (tipePenilaian) {
                case "PA": 
                    maksPartisipasi += nilaiMaksimum; 
                    totalNilaiPartisipasi += nilaiDicapai; 
                    break;
                case "T": 
                    maksTugas += nilaiMaksimum; 
                    totalNilaiTugas += nilaiDicapai; 
                    break;
                case "K": 
                    maksKuis += nilaiMaksimum; 
                    totalNilaiKuis += nilaiDicapai; 
                    break;
                case "P": 
                    maksProyek += nilaiMaksimum; 
                    totalNilaiProyek += nilaiDicapai; 
                    break;
                case "UTS": 
                    maksUTS += nilaiMaksimum; 
                    totalNilaiUTS += nilaiDicapai; 
                    break;
                case "UAS": 
                    maksUAS += nilaiMaksimum; 
                    totalNilaiUAS += nilaiDicapai; 
                    break;
                default:
                    break;
            }
        }

        double persentasePA = (maksPartisipasi == 0) ? 0 : (totalNilaiPartisipasi * 100.0 / maksPartisipasi);
        double persentaseTugas = (maksTugas == 0) ? 0 : (totalNilaiTugas * 100.0 / maksTugas);
        double persentaseKuis = (maksKuis == 0) ? 0 : (totalNilaiKuis * 100.0 / maksKuis);
        double persentaseProyek = (maksProyek == 0) ? 0 : (totalNilaiProyek * 100.0 / maksProyek);
        double persentaseUTS = (maksUTS == 0) ? 0 : (totalNilaiUTS * 100.0 / maksUTS);
        double persentaseUAS = (maksUAS == 0) ? 0 : (totalNilaiUAS * 100.0 / maksUAS);

        int nilaiBulatPA = (int) Math.floor(persentasePA);
        int nilaiBulatTugas = (int) Math.floor(persentaseTugas);
        int nilaiBulatKuis = (int) Math.floor(persentaseKuis);
        int nilaiBulatProyek = (int) Math.floor(persentaseProyek);
        int nilaiBulatUTS = (int) Math.floor(persentaseUTS);
        int nilaiBulatUAS = (int) Math.floor(persentaseUAS);

        double kontribusiPA = (nilaiBulatPA / 100.0) * bobotPartisipasi;
        double kontribusiTugas = (nilaiBulatTugas / 100.0) * bobotTugas;
        double kontribusiKuis = (nilaiBulatKuis / 100.0) * bobotKuis;
        double kontribusiProyek = (nilaiBulatProyek / 100.0) * bobotProyek;
        double kontribusiUTS = (nilaiBulatUTS / 100.0) * bobotUjianTengah;
        double kontribusiUAS = (nilaiBulatUAS / 100.0) * bobotUjianAkhir;

        double totalSkorAkhir = kontribusiPA + kontribusiTugas + kontribusiKuis + 
                               kontribusiProyek + kontribusiUTS + kontribusiUAS;

        StringBuilder hasilPenilaian = new StringBuilder();
        hasilPenilaian.append("Perolehan Nilai:<br/>");
        hasilPenilaian.append(String.format(">> Partisipatif: %d/100 (%.2f/%d)<br/>", 
            nilaiBulatPA, kontribusiPA, bobotPartisipasi));
        hasilPenilaian.append(String.format(">> Tugas: %d/100 (%.2f/%d)<br/>", 
            nilaiBulatTugas, kontribusiTugas, bobotTugas));
        hasilPenilaian.append(String.format(">> Kuis: %d/100 (%.2f/%d)<br/>", 
            nilaiBulatKuis, kontribusiKuis, bobotKuis));
        hasilPenilaian.append(String.format(">> Proyek: %d/100 (%.2f/%d)<br/>", 
            nilaiBulatProyek, kontribusiProyek, bobotProyek));
        hasilPenilaian.append(String.format(">> UTS: %d/100 (%.2f/%d)<br/>", 
            nilaiBulatUTS, kontribusiUTS, bobotUjianTengah));
        hasilPenilaian.append(String.format(">> UAS: %d/100 (%.2f/%d)<br/>", 
            nilaiBulatUAS, kontribusiUAS, bobotUjianAkhir));
        hasilPenilaian.append("<br/>");
        hasilPenilaian.append(String.format(">> Nilai Akhir: %.2f<br/>", totalSkorAkhir));
        hasilPenilaian.append(String.format(">> Grade: %s", tentukanGrade(totalSkorAkhir)));

        return hasilPenilaian.toString();
    }

    private String tentukanGrade(double skorNumerik) {
        if (skorNumerik >= 79.5) return "A";
        else if (skorNumerik >= 72) return "AB";
        else if (skorNumerik >= 64.5) return "B";
        else if (skorNumerik >= 57) return "BC";
        else if (skorNumerik >= 49.5) return "C";
        else if (skorNumerik >= 34) return "D";
        else return "E";
    }

    @GetMapping("/perbedaan-l/{strBase64}")
    public String perbedaanL(@PathVariable String strBase64) {
        byte[] dataDekode;
        try {
            dataDekode = Base64.getDecoder().decode(strBase64);
        } catch (IllegalArgumentException e) {
            return "Input Base64 tidak valid.";
        }

        String stringDekode = new String(dataDekode);
        String[] barisMatriks = stringDekode.split("\\r?\\n");
        
        int dimensi = Integer.parseInt(barisMatriks[0]);
        int[][] gridData = new int[dimensi][dimensi];
        
        for (int baris = 0; baris < dimensi; baris++) {
            String[] nilaiBaris = barisMatriks[baris + 1].split(" ");
            for (int kolom = 0; kolom < dimensi; kolom++) {
                gridData[baris][kolom] = Integer.parseInt(nilaiBaris[kolom]);
            }
        }

        int jumlahL = 0;
        int jumlahLBalik = 0;
        int nilaiPusat = 0;
        
        StringBuilder outputAnalisis = new StringBuilder();

        switch (dimensi) {
            case 1:
                nilaiPusat = gridData[0][0];
                outputAnalisis.append("Nilai L: Tidak Ada<br/>");
                outputAnalisis.append("Nilai Kebalikan L: Tidak Ada<br/>");
                outputAnalisis.append("Nilai Tengah: ").append(nilaiPusat).append("<br/>");
                outputAnalisis.append("Perbedaan: Tidak Ada<br/>");
                outputAnalisis.append("Dominan: ").append(nilaiPusat);
                break;
            case 2:
                for (int i = 0; i < dimensi; i++) {
                    for (int j = 0; j < dimensi; j++) {
                        nilaiPusat += gridData[i][j];
                    }
                }
                outputAnalisis.append("Nilai L: Tidak Ada<br/>");
                outputAnalisis.append("Nilai Kebalikan L: Tidak Ada<br/>");
                outputAnalisis.append("Nilai Tengah: ").append(nilaiPusat).append("<br/>");
                outputAnalisis.append("Perbedaan: Tidak Ada<br/>");
                outputAnalisis.append("Dominan: ").append(nilaiPusat);
                break;
            default:
                for (int i = 0; i < dimensi; i++) { 
                    jumlahL += gridData[i][0]; 
                }
                for (int j = 1; j < dimensi; j++) { 
                    jumlahL += gridData[dimensi - 1][j]; 
                } 

                for (int j = 0; j < dimensi; j++) { 
                    jumlahLBalik += gridData[0][j]; 
                }
                for (int i = 1; i < dimensi; i++) { 
                    jumlahLBalik += gridData[i][dimensi - 1]; 
                }

                nilaiPusat = (dimensi % 2 == 1)
                    ? gridData[dimensi / 2][dimensi / 2]
                    : gridData[dimensi / 2 - 1][dimensi / 2 - 1] + 
                      gridData[dimensi / 2 - 1][dimensi / 2] +
                      gridData[dimensi / 2][dimensi / 2 - 1] + 
                      gridData[dimensi / 2][dimensi / 2];

                int selisihNilai = Math.abs(jumlahL - jumlahLBalik);
                int nilaiDominan = (jumlahL > jumlahLBalik) ? jumlahL
                            : (jumlahLBalik > jumlahL ? jumlahLBalik : nilaiPusat);

                outputAnalisis.append("Nilai L: ").append(jumlahL).append("<br/>");
                outputAnalisis.append("Nilai Kebalikan L: ").append(jumlahLBalik).append("<br/>");
                outputAnalisis.append("Nilai Tengah: ").append(nilaiPusat).append("<br/>");
                outputAnalisis.append("Perbedaan: ").append(selisihNilai).append("<br/>");
                outputAnalisis.append("Dominan: ").append(nilaiDominan);
                break;
        }

        return outputAnalisis.toString();
    }

    /**
     * Menganalisis data numerik untuk menemukan nilai ekstrem dan frekuensi
     */
    @PostMapping("/paling-ter")
    public String palingTer(@RequestBody String inputBase64) {
        String teksDekode;
        try {
            if (inputBase64 == null || inputBase64.isEmpty()) {
                teksDekode = "";
            } else {
                teksDekode = new String(Base64.getDecoder().decode(inputBase64));
            }
        } catch (Exception e) {
            return "Input Base64 tidak valid.";
        }

        String[] barisData = teksDekode.split("\\r?\\n");
        List<Integer> kumpulanAngka = new ArrayList<>();
        Map<Integer, Integer> frekuensiAngka = new HashMap<>();

        for (String baris : barisData) {
            if ("---".equals(baris.trim())) {
                break;
            }
            try {
                int angka = Integer.parseInt(baris.trim());
                kumpulanAngka.add(angka);
                frekuensiAngka.put(angka, frekuensiAngka.getOrDefault(angka, 0) + 1);
            } catch (NumberFormatException e) {
                // Lewati baris yang tidak valid
            }
        }

        if (kumpulanAngka.isEmpty()) {
            return "Tidak ada data untuk diproses.";
        }

        int nilaiTertinggi = Collections.max(kumpulanAngka);
        int nilaiTerendah = Collections.min(kumpulanAngka);

        int frekuensiMaks = 0;
        int frekuensiMin = Integer.MAX_VALUE;
        for (int frekuensi : frekuensiAngka.values()) {
            if (frekuensi > frekuensiMaks) frekuensiMaks = frekuensi;
            if (frekuensi < frekuensiMin) frekuensiMin = frekuensi;
        }

        int nilaiTerbanyak = Integer.MIN_VALUE; 
        for (Map.Entry<Integer, Integer> entry : frekuensiAngka.entrySet()) {
            if (entry.getValue() == frekuensiMaks) {
                if (entry.getKey() > nilaiTerbanyak) { 
                    nilaiTerbanyak = entry.getKey(); 
                }
            }
        }
        
        int nilaiTersedikit = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Integer> entry : frekuensiAngka.entrySet()) {
            if (entry.getValue() == frekuensiMin) {
                if (entry.getKey() < nilaiTersedikit) {
                    nilaiTersedikit = entry.getKey(); 
                }
            }
        }
        
        long totalTertinggi = (long) nilaiTerbanyak * frekuensiMaks;
        long totalTerendah = (long) nilaiTerendah * frekuensiMaks;
        
        return new StringBuilder()
                .append("Tertinggi: ").append(nilaiTertinggi).append("<br/>")
                .append("Terendah: ").append(nilaiTerendah).append("<br/>")
                .append("Terbanyak: ").append(nilaiTerbanyak).append(" (").append(frekuensiMaks).append("x)").append("<br/>")
                .append("Tersedikit: ").append(nilaiTersedikit).append(" (").append(frekuensiMin).append("x)").append("<br/>")
                .append("Jumlah Tertinggi: ").append(frekuensiMaks).append(" * ").append(nilaiTerbanyak).append(" = ").append(totalTertinggi).append("<br/>")
                .append("Jumlah Terendah: ").append(nilaiTerendah).append(" * ").append(frekuensiMaks).append(" = ").append(totalTerendah)
                .toString();
    }
}