package org.delcom.starter.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HomeControllerUnitTest {
    // Test untuk metode hello()
    @Test
    @DisplayName("Mengembalikan pesan selamat datang yang benar")
    void hello_ShouldReturnWelcomeMessage() throws Exception {
        // Arrange
        HomeController controller = new HomeController();

        // Act
        String result = controller.hello();

        // Assert
        assertEquals("Hay Abdullah, selamat datang di pengembangan aplikasi dengan Spring Boot!", result);
    }

    // Tambahan test untuk metode sayHello dengan parameter nama
    @Test
    @DisplayName("Mengembalikan pesan sapaan yang dipersonalisasi")
    void helloWithName_ShouldReturnPersonalizedGreeting() throws Exception {
        // Arrange
        HomeController controller = new HomeController();

        // Act
        String result = controller.sayHello("Abdullah");

        // Assert
        assertEquals("Hello, Abdullah!", result);
    }

    // Test untuk metode informasiNim()
    @Test
    @DisplayName("Menguji semua kemungkinan NIM valid dan tidak valid")
    void informasiNIM_semua_kemungkinan_nim_valid_dan_tidak_valid() throws Exception {
        // Test NIM Tidak Valid
        {
            String input = "11S180";
            String expected = "NIM harus 8 karakter";

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.informasiNim(input);

            // Assert
            assertEquals(expected, result);
        }

        // Test NIM Valid dengan Prodi Tidak Tersedia
        {
            String input = "ZZS18005";
            String expected = "Program Studi tidak Tersedia";

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.informasiNim(input);

            // Assert
            assertEquals(expected, result);
        }

        // Test Sarjan Informatika
        {
            String input = "11S18005";
            String expected = "Inforamsi NIM 11S18005: >> Program Studi: Sarjana Informatika>> Angkatan: 2018>> Urutan: 5";

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.informasiNim(input);

            // Assert
            assertEquals(expected, result);
        }

        // Test Sarjana Sistem Informasi
        {
            String input = "12S18005";
            String expected = "Inforamsi NIM 12S18005: >> Program Studi: Sarjana Sistem Informasi>> Angkatan: 2018>> Urutan: 5";

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.informasiNim(input);

            // Assert
            assertEquals(expected, result);
        }

        // Test Sarjana Teknik Elektro
        {
            String input = "14S18005";
            String expected = "Inforamsi NIM 14S18005: >> Program Studi: Sarjana Teknik Elektro>> Angkatan: 2018>> Urutan: 5";

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.informasiNim(input);

            // Assert
            assertEquals(expected, result);
        }

        // Test Sarjana Manajemen Rekayasa
        {
            String input = "21S18005";
            String expected = "Inforamsi NIM 21S18005: >> Program Studi: Sarjana Manajemen Rekayasa>> Angkatan: 2018>> Urutan: 5";

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.informasiNim(input);

            // Assert
            assertEquals(expected, result);
        }

        // Test Sarjana Teknik Metalurgi
        {
            String input = "22S18005";
            String expected = "Inforamsi NIM 22S18005: >> Program Studi: Sarjana Teknik Metalurgi>> Angkatan: 2018>> Urutan: 5";

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.informasiNim(input);

            // Assert
            assertEquals(expected, result);
        }

        // Test Sarjana Teknik Bioproses
        {
            String input = "31S18005";
            String expected = "Inforamsi NIM 31S18005: >> Program Studi: Sarjana Teknik Bioproses>> Angkatan: 2018>> Urutan: 5";

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.informasiNim(input);

            // Assert
            assertEquals(expected, result);
        }

        // Test Diploma 4 Teknologi Rekasaya Perangkat Lunak
        {
            String input = "11418005";
            String expected = "Inforamsi NIM 11418005: >> Program Studi: Diploma 4 Teknologi Rekasaya Perangkat Lunak>> Angkatan: 2018>> Urutan: 5";

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.informasiNim(input);

            // Assert
            assertEquals(expected, result);
        }

        // Test Diploma 3 Teknologi Informasi
        {
            String input = "11318005";
            String expected = "Inforamsi NIM 11318005: >> Program Studi: Diploma 3 Teknologi Informasi>> Angkatan: 2018>> Urutan: 5";

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.informasiNim(input);

            // Assert
            assertEquals(expected, result);
        }

        // Test Diploma 3 Teknologi Komputer
        {
            String input = "13318005";
            String expected = "Inforamsi NIM 13318005: >> Program Studi: Diploma 3 Teknologi Komputer>> Angkatan: 2018>> Urutan: 5";

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.informasiNim(input);

            // Assert
            assertEquals(expected, result);
        }
    }

    // Test untuk metode perbedaanL()
    @Test
    @DisplayName("Menghitung perbedaan L pada matriks dengan berbagai ukuran")
    void perbedaanL_matriks_berbagai_ukuran() throws Exception {
        // Test matriks 1x1
        {
            String inputBase64 = "MQ0KMTkNCg==";
            String expected = """
                    Nilai L: Tidak Ada
                    Nilai Kebalikan L: Tidak Ada
                    Nilai Tengah: 19
                    Perbedaan: Tidak Ada
                    Dominan: 19
                    """;
            expected = expected.replaceAll("\n", "<br/>").trim();

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.perbedaanL(inputBase64);

            // Assert
            assertEquals(expected, result);
        }

        // Test matriks 2x2
        {
            String inputBase64 = "Mg0KODMgNTUNCjY2IDc1DQo=";
            String expected = """
                    Nilai L: Tidak Ada
                    Nilai Kebalikan L: Tidak Ada
                    Nilai Tengah: 279
                    Perbedaan: Tidak Ada
                    Dominan: 279
                    """;
            expected = expected.replaceAll("\n", "<br/>").trim();

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.perbedaanL(inputBase64);

            // Assert
            assertEquals(expected, result);
        }

        // Test matriks 3x3
        {
            String inputBase64 = "Mw0KMSAyIDMNCjQgNSA2DQo3IDggOQ0K";
            String expected = """
                    Nilai L: 20
                    Nilai Kebalikan L: 20
                    Nilai Tengah: 5
                    Perbedaan: 0
                    Dominan: 5
                    """;
            expected = expected.replaceAll("\n", "<br/>").trim();

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.perbedaanL(inputBase64);

            // Assert
            assertEquals(expected, result);
        }

        // Test Matrik 16x16
        {
            String inputBase64 = "MTYNCjg2IDIgMTkgNjIgOTkgOTQgODggNzEgNjggMzQgMzQgNTEgNzQgNjkgNDkgMjYNCjg4IDg3IDc4IDk5IDQxIDYyIDI3IDI3IDM3IDQ3IDY2IDc3IDMgMTEgNDIgNTcNCjk0IDUxIDg3IDkgMjUgODAgNjMgNDMgMzkgNDUgNCA1IDg0IDUyIDMgODkNCjczIDI3IDM0IDcyIDkyIDk1IDQgNzggODEgNzkgODggNTAgNjQgOTUgMSAyOA0KOTkgNjIgNDYgMjggMTQgOTggMTIgNjcgMTAgNTEgMzMgMTYgNzYgMjMgMTQgMjENCjY5IDQzIDU4IDM1IDE4IDQzIDgyIDggNDUgOTcgMzAgNDAgMTkgOTkgODUgMzINCjYyIDExIDc1IDgzIDYxIDYyIDc3IDEwIDYwIDQ4IDI1IDY2IDM3IDY1IDQ2IDkyDQozNyAzMCAzMyAyNCA4MyA3NyA5MSA3NSAxMSA0NyAxIDkxIDEwMCA5OSA0NyA5OA0KNjQgNDQgOSAxMDAgOTkgODcgMzYgMSA3NyA1IDE5IDM3IDE2IDg2IDE0IDM3DQo0NSA2OSA2NiAzMyAzOCA3IDIgNTMgMzQgNjUgNDYgODcgNzIgMjQgMTEgNQ0KNDMgNzggMiA1NSA0NiAxMSAzOCA0NiA3OSA2NyAxMCA3OSAyIDI5IDcwIDkzDQo4MSA0NSA4NiA2MCA1MCA5NiAzMSAzNCA3MiA5MSA4MSAzNCA5MSA0MCA0MSA2OQ0KMzQgMTggOTMgOTQgOTYgMTEgOCA3NyAzMCA3MSA0IDQ1IDY4IDY3IDQ1IDg4DQo0MyA0OSA4OSA1NSA4OSA2OCAzNCA3NSA1NiA1IDI4IDI1IDExIDY4IDM0IDEyDQo1NCA0NiA4MCA1NiAzMiA2NiA3OCA2MSA3MCA1NCA2MyA0OCAyNCA3OCA5OCA3Mg0KNDAgMTcgNjUgODUgNjYgMjYgOSAzNCA4MiAxNSA3MyAyMiA3OCA3OCA1OSA0OA0K";

            String expected = """
                    Nilai L: 1721
                    Nilai Kebalikan L: 1681
                    Nilai Tengah: 164
                    Perbedaan: 40
                    Dominan: 1721
                    """;
            expected = expected.replaceAll("\n", "<br/>").trim();

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.perbedaanL(inputBase64);

            // Assert
            assertEquals(expected, result);
        }
    }

    // Test untuk metode palingTer()
    @Test
    @DisplayName("Memperolah informasi paling ter dari suatu nilai")
    void palingTer_memperoleh_informasi_paling_ter_dari_suatu_nilai() throws Exception {
        // Pengujian Counter = 0
        {
            String inputBase64 = "LS0tDQo=";
            String expected = "Informasi tidak tersedia";
            expected = expected.trim();

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.palingTer(inputBase64);

            // Assert
            assertEquals(expected, result);
        }

        // Pengujian 1
        {
            String inputBase64 = "MQ0KMQ0KMw0KMw0KMg0KMg0KMg0KNA0KNQ0KMQ0KLS0tDQo=";
            String expected = """
                    Tertinggi: 5
                    Terendah: 1
                    Terbanyak: 2 (3x)
                    Tersedikit: 4 (1x)
                    Jumlah Tertinggi: 3 * 2 = 6
                    Jumlah Terendah: 1 * 3 = 3
                    """;
            expected = expected.replaceAll("\n", "<br/>").trim();

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.palingTer(inputBase64);

            // Assert
            assertEquals(expected, result);
        }

        // Pengujian 2
        {
            String inputBase64 = "NTgNCjMxDQo4NA0KMTINCjg2DQo2MA0KODINCjQ5DQo4Mw0KNjQNCjc3DQo3DQo2OA0KOTENCjMNCjQxDQo1MQ0KNTMNCjUyDQo3NA0KNzINCjc0DQoxMA0KMzgNCjUwDQo1MQ0KOQ0KNTYNCjU2DQoyNQ0KODQNCjYzDQo5OA0KODQNCjQ4DQo5NQ0KNDYNCjMzDQo3OA0KNDUNCjU1DQoxMA0KNjANCjg4DQoxMA0KNzYNCjgxDQo1NQ0KNDYNCjU4DQozOA0KNTcNCjkyDQo2Nw0KNDENCjQ3DQozMA0KMg0KNjANCjQwDQozNg0KNTMNCjcwDQozMw0KNjYNCjYyDQo1OA0KMTUNCjkyDQo3Mw0KMTcNCjM3DQo2Ng0KMTINCjI5DQoxMDANCjUzDQo2Ng0KMzANCjEyDQozNA0KNg0KMjQNCjg5DQo2NA0KMTENCjgwDQozDQo5MA0KOA0KNjcNCjY1DQoyMg0KNTINCjI2DQozMg0KNjINCjU4DQo0NA0KOTQNCjE4DQo1OA0KMTANCjUNCjE3DQozNg0KMTcNCjYzDQo2OQ0KNzYNCjUyDQo1DQo5NA0KODINCjgNCjg1DQo4NQ0KNDcNCjQzDQozOA0KNzcNCjQzDQo0MQ0KNzMNCjgxDQozNg0KMjYNCjE3DQo0NQ0KOQ0KNDANCjczDQo1OQ0KMw0KNTgNCjYzDQoyNg0KMjkNCjE3DQo2OA0KOTQNCjM5DQo1DQo1MA0KMjQNCjQ0DQozNg0KMjQNCjUNCjkyDQo4NQ0KNTQNCjI2DQo5Mg0KNTINCjI1DQoyMw0KNDkNCjQ5DQo0Nw0KMjYNCjQ1DQo2DQo0OA0KNTkNCjk5DQozMA0KMjENCjEyDQoyMA0KNjENCjU2DQo4OA0KNDENCjQ4DQo1Nw0KNzQNCjY0DQozNA0KNjENCjEwDQo2DQo0OQ0KNjYNCjM1DQo1DQoyNg0KODUNCjc1DQo0OQ0KODkNCjY4DQoxMQ0KMTcNCjUNCjg0DQoxMQ0KNjINCjkzDQoyNw0KODkNCjg2DQo0NA0KNA0KOTMNCjI4DQo2Nw0KNDINCjY2DQoyDQo3Mg0KMTENCjEwDQo0NQ0KNTANCjMyDQoxOA0KMzANCjk1DQoyNQ0KMjINCjI2DQoxMDANCjI3DQo3Mw0KNDcNCjUNCjIwDQo4OA0KODkNCjcNCjk3DQoyOA0KMjUNCjQ0DQo3Nw0KMTENCjQ2DQo5Ng0KOTMNCjY3DQo1MQ0KNDkNCjY1DQo3OQ0KODkNCjg5DQotLS0NCg==";
            String expected = """
                    Tertinggi: 100
                    Terendah: 2
                    Terbanyak: 26 (7x)
                    Tersedikit: 35 (1x)
                    Jumlah Tertinggi: 89 * 6 = 534
                    Jumlah Terendah: 2 * 2 = 4
                    """;
            expected = expected.replaceAll("\n", "<br/>").trim();

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.palingTer(inputBase64);

            // Assert
            assertEquals(expected, result);
        }
    }
}
