package org.delcom.starter.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.DisplayName;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HomeControllerUnitTest {
    
    @Test
    @DisplayName("Mengembalikan pesan selamat datang yang benar")
    void hello_ShouldReturnWelcomeMessage() throws Exception {
        HomeController controller = new HomeController();
        String result = controller.hello();
        assertEquals("Hay Abdullah Ubaid, selamat datang di pengembangan aplikasi dengan Spring Boot!", result);
    }

    @Test
    @DisplayName("Mengembalikan pesan sapaan yang dipersonalisasi")
    void helloWithName_ShouldReturnPersonalizedGreeting() throws Exception {
        HomeController controller = new HomeController();
        String result = controller.sayHello("Abdullah");
        assertEquals("Hello, Abdullah!", result);
    }

    @Test
    @DisplayName("Menguji semua kemungkinan NIM valid dan tidak valid")
    void informasiNIM_semua_kemungkinan_nim_valid_dan_tidak_valid() throws Exception {
        // Test NIM Tidak Valid
        {
            String input = "11S180";
            String expected = "Informasi NIM 11S180:<br/>>> Program Studi: Sarjana Informatika<br/>>> Angkatan: 2018<br/>>> Urutan: 180";
            HomeController controller = new HomeController();
            String result = controller.informasiNim(input);
            assertEquals(expected, result);
        }

        // Test NIM Valid dengan Prodi Tidak Tersedia
        {
            String input = "ZZS18005";
            String expected = "Program Studi tidak Tersedia";
            HomeController controller = new HomeController();
            String result = controller.informasiNim(input);
            assertEquals(expected, result);
        }

        // Test berbagai program studi
        String[] testCases = {
            "11S18005", "12S18005", "14S18005", "21S18005", "22S18005", 
            "31S18005", "11418005", "11318005", "13318005"
        };
        
        String[] expectedResults = {
            "Inforamsi NIM 11S18005: >> Program Studi: Sarjana Informatika>> Angkatan: 2018>> Urutan: 5",
            "Inforamsi NIM 12S18005: >> Program Studi: Sarjana Sistem Informasi>> Angkatan: 2018>> Urutan: 5",
            "Inforamsi NIM 14S18005: >> Program Studi: Sarjana Teknik Elektro>> Angkatan: 2018>> Urutan: 5",
            "Inforamsi NIM 21S18005: >> Program Studi: Sarjana Manajemen Rekayasa>> Angkatan: 2018>> Urutan: 5",
            "Inforamsi NIM 22S18005: >> Program Studi: Sarjana Teknik Metalurgi>> Angkatan: 2018>> Urutan: 5",
            "Inforamsi NIM 31S18005: >> Program Studi: Sarjana Teknik Bioproses>> Angkatan: 2018>> Urutan: 5",
            "Inforamsi NIM 11418005: >> Program Studi: Diploma 4 Teknologi Rekasaya Perangkat Lunak>> Angkatan: 2018>> Urutan: 5",
            "Inforamsi NIM 11318005: >> Program Studi: Diploma 3 Teknologi Informasi>> Angkatan: 2018>> Urutan: 5",
            "Inforamsi NIM 13318005: >> Program Studi: Diploma 3 Teknologi Komputer>> Angkatan: 2018>> Urutan: 5"
        };

        for (int i = 0; i < testCases.length; i++) {
            HomeController controller = new HomeController();
            String result = controller.informasiNim(testCases[i]);
            assertEquals(expectedResults[i], result);
        }
    }

    @Test
    @DisplayName("Test Perolehan Nilai - Should cover all grades and formats")
    void perolehanNilai_ShouldCoverAllGradesAndCases() {
        HomeController controller = new HomeController();
        
        // Test case spesifik
        String yourInput = "0\n35\n1\n16\n22\n26\nT|90|21\nUAS|92|82\nUAS|63|15\nT|10|5\nUAS|89|74\nT|95|35\nPA|75|45\nPA|90|77\nPA|86|14\nUTS|21|0\nK|50|44\n---";
        String base64YourInput = "MA0KMzUNCjENCjE2DQoyMg0KMjYNClR8OTB8MjENClVBU3w5Mnw4Mg0KVUFTfDYzfDE1DQpUfDEwfDUNClVBU3w4OXw3NA0KVHw5NXwzNQ0KUEF8NzV8NDUNClBBfDkwfDc3DQpQQXw4NnwxNA0KVVRTfDIxfDANCkt8NTB8NDQNCi0tLQ0K";
        String resultFromYourInput = controller.perolehanNilai(base64YourInput);
        assertTrue(resultFromYourInput.contains(">> Nilai Akhir: 29.93"));
        assertTrue(resultFromYourInput.contains(">> Grade: E"));

        // Test semua grade
        String weights = "10\n10\n10\n20\n25\n25";
        String[] testInputs = {
            weights + "\nPA|100|80\nT|100|80\nK|100|80\nP|100|80\nUTS|100|80\nUAS|100|80\n---",
            weights + "\nPA|100|75\nT|100|75\nK|100|75\nP|100|75\nUTS|100|75\nUAS|100|75\n---",
            weights + "\nPA|100|70\nT|100|70\nK|100|70\nP|100|70\nUTS|100|70\nUAS|100|70\n---",
            weights + "\nPA|100|60\nT|100|60\nK|100|60\nP|100|60\nUTS|100|60\nUAS|100|60\n---",
            weights + "\nPA|100|50\nT|100|50\nK|100|50\nP|100|50\nUTS|100|50\nUAS|100|50\n---",
            weights + "\nPA|100|40\nT|100|40\nK|100|40\nP|100|40\nUTS|100|40\nUAS|100|40\n---",
            weights + "\nINVALID|100|100\n---"
        };
        
        String[] expectedGrades = {"A", "AB", "B", "BC", "C", "D", "E"};
        
        for (int i = 0; i < testInputs.length; i++) {
            String encodedInput = Base64.getEncoder().encodeToString(testInputs[i].getBytes());
            assertTrue(controller.perolehanNilai(encodedInput).contains(">> Grade: " + expectedGrades[i]));
        }
    }
    
    @Test
    @DisplayName("Test Perbedaan L - Should cover all N sizes and dominant cases")
    void perbedaanL_ShouldCoverAllCases() {
        HomeController controller = new HomeController();
        
        String[] testInputs = {
            "1\n5",
            "2\n1 2\n3 4", 
            "3\n1 2 3\n4 5 6\n7 8 9",
            "4\n10 1 1 1\n10 1 1 1\n10 1 1 1\n10 1 1 1",
            "3\n1 10 10\n1 1 10\n1 1 10",
            "3\n1 1 1\n1 9 1\n1 1 1"
        };
        
        String[] expectedContents = {
            "Dominan: 5",
            "Dominan: 10", 
            "Dominan: 29",
            "Dominan: 43",
            "Dominan: 41",
            "Dominan: 9"
        };
        
        for (int i = 0; i < testInputs.length; i++) {
            String encodedInput = Base64.getEncoder().encodeToString(testInputs[i].getBytes());
            assertTrue(controller.perbedaanL(encodedInput).contains(expectedContents[i]));
        }
    }

    @Test
    @DisplayName("Memperolah informasi paling ter dari suatu nilai")
    void palingTer_memperoleh_informasi_paling_ter_dari_suatu_nilai() throws Exception {
        HomeController controller = new HomeController();
        
        // Test case 1: Data kosong
        {
            String inputBase64 = "LS0tDQo=";
            String expected = "Tidak ada data untuk diproses.";
            String result = controller.palingTer(inputBase64);
            assertEquals(expected, result.trim());
        }

        // Test case 2: Data sederhana
        {
            String inputBase64 = "MQ0KMQ0KMw0KMw0KMg0KMg0KMg0KNA0KNQ0KMQ0KLS0tDQo=";
            String expected = "Tertinggi: 5<br/>Terendah: 1<br/>Terbanyak: 2 (3x)<br/>Tersedikit: 4 (1x)<br/>Jumlah Tertinggi: 3 * 2 = 6<br/>Jumlah Terendah: 1 * 3 = 3";
            String result = controller.palingTer(inputBase64);
            assertEquals(expected, result);
        }

        // Test case 3: Data kompleks (Base64 dari plain.txt test case 1)
        {
            String inputBase64 = "NTgNCjMxDQo4NA0KMTINCjg2DQo2MA0KODINCjQ5DQo4Mw0KNjQNCjc3DQo3DQo2OA0KOTENCjMNCjQxDQo1MQ0KNTMNCjUyDQo3NA0KNzINCjc0DQoxMA0KMzgNCjUwDQo1MQ0KOQ0KNTYNCjU2DQoyNQ0KODQNCjYzDQo5OA0KODQNCjQ4DQo5NQ0KNDYNCjMzDQo3OA0KNDUNCjU1DQoxMA0KNjANCjg4DQoxMA0KNzYNCjgxDQo1NQ0KNDYNCjU4DQozOA0KNTcNCjkyDQo2Nw0KNDENCjQ3DQozMA0KMg0KNjANCjQwDQozNg0KNTMNCjcwDQozMw0KNjYNCjYyDQo1OA0KMTUNCjkyDQo3Mw0KMTcNCjM3DQo2Ng0KMTINCjI5DQoxMDANCjUzDQo2Ng0KMzANCjEyDQozNA0KNg0KMjQNCjg5DQo2NA0KMTENCjgwDQozDQo5MA0KOA0KNjcNCjY1DQoyMg0KNTINCjI2DQozMg0KNjINCjU4DQo0NA0KOTQNCjE4DQo1OA0KMTANCjUNCjE3DQozNg0KMTcNCjYzDQo2OQ0KNzYNCjUyDQo1DQo5NA0KODINCjgNCjg1DQo4NQ0KNDcNCjQzDQozOA0KNzcNCjQzDQo0MQ0KNzMNCjgxDQozNg0KMjYNCjE3DQo0NQ0KOQ0KNDANCjczDQo1OQ0KMw0KNTgNCjYzDQoyNg0KMjkNCjE3DQo2OA0KOTQNCjM5DQo1DQo1MA0KMjQNCjQ0DQozNg0KMjQNCjUNCjkyDQo4NQ0KNTQNCjI2DQo5Mg0KNTINCjI1DQoyMw0KNDkNCjQ5DQo0Nw0KMjYNCjQ1DQo2DQo0OA0KNTkNCjk5DQozMA0KMjENCjEyDQoyMA0KNjENCjU2DQo4OA0KNDENCjQ4DQo1Nw0KNzQNCjY0DQozNA0KNjENCjEwDQo2DQo0OQ0KNjYNCjM1DQo1DQoyNg0KODUNCjc1DQo0OQ0KODkNCjY4DQoxMQ0KMTcNCjUNCjg0DQoxMQ0KNjINCjkzDQoyNw0KODkNCjg2DQo0NA0KNA0KOTMNCjI4DQo2Nw0KNDINCjY2DQoyDQo3Mg0KMTENCjEwDQo0NQ0KNTANCjMyDQoxOA0KMzANCjk1DQoyNQ0KMjINCjI2DQoxMDANCjI3DQo3Mw0KNDcNCjUNCjIwDQo4OA0KODkNCjcNCjk3DQoyOA0KMjUNCjQ0DQo3Nw0KMTENCjQ2DQo5Ng0KOTMNCjY3DQo1MQ0KNDkNCjY1DQo3OQ0KODkNCjg5DQotLS0NCg==";
            String expected = "Tertinggi: 100<br/>Terendah: 2<br/>Terbanyak: 26 (7x)<br/>Tersedikit: 4 (1x)<br/>Jumlah Tertinggi: 7 * 26 = 182<br/>Jumlah Terendah: 2 * 7 = 14";
            String result = controller.palingTer(inputBase64);
            assertEquals(expected, result);
        }

        // Test case 4: Data kompleks kedua (Base64 dari plain.txt test case 2)
        {
            String inputBase64 = "NTgNCjg1DQo1NA0KMTMNCjM3DQo4OQ0KNzUNCjQzDQo4OA0KMg0KNjENCjI0DQo4MA0KMzMNCjMzDQo3MA0KNDYNCjM1DQo5Ng0KMzINCjUxDQo1NQ0KNTcNCjMyDQo5Nw0KMzENCjc5DQoxOA0KOTMNCjY1DQo4NQ0KOTMNCjczDQo0MQ0KNTQNCjgxDQoxNQ0KNjYNCjY4DQo4Nw0KODANCjU0DQo0MQ0KOTYNCjgyDQo4OA0KNjcNCjk3DQo2NA0KMzkNCjcxDQo3NQ0KMzMNCjI4DQo5OQ0KODANCjk3DQo4OA0KODENCjg2DQo3Mw0KMzQNCjYwDQo5NQ0KMjYNCjY0DQo4MQ0KOTUNCjMNCjgzDQo5NQ0KMTINCjQ4DQo5OA0KMg0KODgNCjE5DQo0OQ0KMzENCjcyDQo4MA0KNzINCjEyDQo5MQ0KOTkNCjc2DQo4MA0KMzANCjExDQo4MA0KNTENCjQwDQoyNQ0KNDUNCjUyDQo0NA0KOTMNCjcyDQoyMw0KNzMNCjI5DQoyOA0KNzANCjk0DQo3NQ0KNDgNCjYzDQo2MA0KNzcNCjE0DQo2MA0KMzgNCjk1DQozNw0KOA0KOA0KMjUNCjUyDQo1Nw0KMzQNCjU3DQoyOA0KMTQNCjM3DQoyMQ0KOQ0KMTUNCjI4DQo3Nw0KMzQNCjY0DQo4NA0KMTgNCjc4DQo5MA0KNTQNCjQxDQo0OQ0KNjENCjQxDQozMg0KNzgNCjMNCjcNCjU3DQo0Nw0KMTINCjI0DQo2OA0KMzMNCjIwDQo2NA0KOTcNCjQyDQoyOQ0KOTUNCjUwDQoyMA0KOA0KODINCjE5DQo1MA0KNg0KNjANCjQzDQozNg0KOQ0KMjANCjczDQo0Nw0KODMNCjQ1DQoyNA0KNTgNCjg1DQoyMA0KNg0KMzkNCjMyDQoxNw0KODQNCjI4DQo3Mw0KOQ0KNDYNCjEzDQo1OA0KNzYNCjc3DQo1NA0KNTAwDQo2NA0KNzcNCjcyDQo1DQo5Mg0KMjUNCg==";
            String result = controller.palingTer(inputBase64);
            // Verifikasi bahwa hasil tidak kosong dan mengandung informasi yang diharapkan
            assertNotNull(result);
            assertTrue(result.contains("Tertinggi"));
            assertTrue(result.contains("Terendah"));
            assertTrue(result.contains("Terbanyak"));
        }
    }

    // Test tambahan untuk coverage 100%
    @Test
    @DisplayName("sayHello: Mengembalikan sapaan default jika nama bukan Abdullah")
    void sayHello_ShouldReturnDefaultGreeting() throws Exception {
        HomeController controller = new HomeController();
        String result = controller.sayHello("Budi");
        assertEquals("Hello, Budi!", result);
    }

    @Test
    @DisplayName("informasiNim: Menangani format NIM yang tidak dikenal")
    void informasiNim_ShouldHandleInvalidFormat() throws Exception {
        HomeController controller = new HomeController();
        String result = controller.informasiNim("123");
        assertEquals("Format NIM tidak dikenal", result);
    }

    @Test
    @DisplayName("perbedaanL: Menangani input Base64 yang tidak valid")
    void perbedaanL_ShouldHandleInvalidBase64() throws Exception {
        HomeController controller = new HomeController();
        String result = controller.perbedaanL("!!!!");
        assertEquals("Input Base64 tidak valid.", result);
    }

    @Test
    @DisplayName("palingTer: Menangani input Base64 yang tidak valid")
    void palingTer_ShouldHandleInvalidBase64() throws Exception {
        HomeController controller = new HomeController();
        String result = controller.palingTer("!!!!");
        assertEquals("Input Base64 tidak valid.", result);
    }

    @Test
    @DisplayName("palingTer: Mengabaikan baris yang bukan angka")
    void palingTer_ShouldHandleInvalidNumberFormat() throws Exception {
        HomeController controller = new HomeController();
        String inputBase64 = "MTANCnNhdHUNCjIwDQotLS0NCg==";
        String result = controller.palingTer(inputBase64);
        String expected = "Tertinggi: 20<br/>Terendah: 10<br/>Terbanyak: 20 (1x)<br/>Tersedikit: 10 (1x)<br/>Jumlah Tertinggi: 1 * 20 = 20<br/>Jumlah Terendah: 10 * 1 = 10";
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("palingTer: Menangani input Base64 yang kosong")
    void palingTer_ShouldHandleEmptyBase64Input() throws Exception {
        HomeController controller = new HomeController();
        String result = controller.palingTer("");
        assertEquals("Tidak ada data untuk diproses.", result);
    }

    @Test
    @DisplayName("palingTer: Menangani input null")
    void palingTer_ShouldHandleNullInput() throws Exception {
        HomeController controller = new HomeController();
        String result = controller.palingTer(null);
        assertEquals("Tidak ada data untuk diproses.", result);
    }

    @Test
    @DisplayName("perolehanNilai: Menangani input tanpa data nilai (hanya bobot)")
    void perolehanNilai_ShouldHandleNoGrades() throws Exception {
        HomeController controller = new HomeController();
        String input = "10\n10\n10\n20\n25\n25";
        String base64Input = Base64.getEncoder().encodeToString(input.getBytes());
        String result = controller.perolehanNilai(base64Input);
        assertTrue(result.contains(">> Nilai Akhir: 0.00"));
        assertTrue(result.contains(">> Grade: E"));
    }
}