/**
 * Ad Soyad: Cansude Sayın
 * Öğrenci No: 250541096
 * Proje: Sinema Bileti, uygulaması kullanıcının seçtiği gün, saat, yaş, meslek ve film formatına göre temel bileti belirleyip;
 hafta sonu, matine, öğrenci indirimi, 65+ indirimleri ve format ek ücretlerini kullanarak dinamik bilet fiyatı hesaplayan bir sistemdir.
 * Tarih: 27.11.2025
 */
import java.util.Scanner;

public class SinemaBileti {
    //temel fiyat sabitleri
   public static final double WEEKDAY_MATINE = 45.0;
   public static final double WEEKDAY_NORMAL = 65.0;
   public static final double WEEKEND_MATINE = 55.0;
   public static final double WEEKEND_NORMAL = 85.0;


   //film formatı ek ücretleri
    public static final double EXTRA_2D  = 0.0;
    public static final double EXTRA_3D  = 25.0;
    public static final double EXTRA_IMAX = 35.0;
    public static final double EXTRA_4DX  = 50.0;

    // 1) hafta sonu mu?
    //1=Pzt, 2=Salı, 3=Çrş, 4=Prş, 5=Cuma, 6=Cmt, 7=Paz
    public static boolean isWeekend(int gun) {
        return gun == 6 || gun == 7;
    }
    // 2) Matine mi? (12:00 öncesi)
    public static boolean isMatinee(int saat) {
        return saat < 12;
    }

    // 3) Temel bilet fiyatını hesapla (gün + saat)
    public static double calculateBasePrice(int gun, int saat) {
        boolean weekend = isWeekend(gun);
        boolean matinee = isMatinee(saat);

        if (!weekend && matinee) {              // Hafta içi matine
            return WEEKDAY_MATINE;
        } else if (!weekend) {                  // Hafta içi normal
            return WEEKDAY_NORMAL;
        } else if (weekend && matinee) {        // Hafta sonu matine
            return WEEKEND_MATINE;
        } else {                                // Hafta sonu normal
            return WEEKEND_NORMAL;
        }
    }

    // 4) İndirim oranını hesapla (0.0–1.0 arası)
    // meslek: 1=Öğrenci, 2=Öğretmen, 3=Diğer
    // gün: 1=Pzt, ..., 7=Paz
    public static double calculateDiscount(int yas, int meslekKodu, int gun) {
        // 65+ yaş: %30
        if (yas >= 65) {
            return 0.30;
        }
        // 12 yaş altı: %25
        if (yas < 12) {
            return 0.25;
        }

        // Öğrenci: %20 (Pzt–Prş), %15 (Cum–Paz)
        if (meslekKodu == 1) { // öğrenci
            if (gun >= 1 && gun <= 4) {         // Pzt–Prş
                return 0.20;
            } else if (gun >= 5 && gun <= 7) {  // Cum–Paz
                return 0.15;
            }
        }

        // Öğretmen: %35 (sadece Çarşamba = 3)
        if (meslekKodu == 2 && gun == 3) {
            return 0.35;
        }

        // Diğer durumlarda indirim yok
        return 0.0;
    }

    // 5) Film türüne göre ekstra ücret
    // filmTuru: 1=2D, 2=3D, 3=IMAX, 4=4DX
    public static double getFormatExtra(int filmTuru) {
        switch (filmTuru) {
            case 1:  // 2D
                return EXTRA_2D;
            case 2:  // 3D
                return EXTRA_3D;
            case 3:  // IMAX
                return EXTRA_IMAX;
            case 4:  // 4DX
                return EXTRA_4DX;
            default:
                return 0.0;
        }
    }

    // 6) Final fiyatı hesaplama
    // (temel fiyat + format ekstra) * (1 - indirimOranı)
    public static double calculateFinalPrice(double basePrice, double formatExtra, double discountRate) {
        double araTutar = basePrice + formatExtra;
        double indirimli = araTutar * (1.0 - discountRate);
        return indirimli;
    }

    // 7) Bilet bilgilerini ekrana yazdırma
    public static void generateTicketInfo(
            String gunAdi,
            int saat,
            int yas,
            String meslekAdi,
            String formatAdi,
            double basePrice,
            double formatExtra,
            double discountRate,
            double finalPrice
    ) {
        System.out.println("\n===== BILET OZETI =====");
        System.out.printf("Gun           : %s%n", gunAdi);
        System.out.printf("Seans         : %02d:00%n", saat);
        System.out.printf("Yas           : %d%n", yas);
        System.out.printf("Meslek        : %s%n", meslekAdi);
        System.out.printf("Film Formati  : %s%n", formatAdi);

        System.out.println("------------------------");
        System.out.printf("Temel Fiyat   : %.2f TL%n", basePrice);
        System.out.printf("Format Ekstra : %.2f TL%n", formatExtra);
        System.out.printf("Indirim Orani : %.0f%%%n", discountRate * 100);
        System.out.println("------------------------");
        System.out.printf("Odenecek Tutar: %.2f TL%n", finalPrice);
        System.out.println("========================\n");
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("=== SINEMA BILETI FIYATLANDIRMA SISTEMI ===\n");

        // GÜN SEÇİMİ 
        System.out.println("Gun secin:");
        System.out.println("1) Pazartesi");
        System.out.println("2) Sali");
        System.out.println("3) Carsamba");
        System.out.println("4) Persembe");
        System.out.println("5) Cuma");
        System.out.println("6) Cumartesi");
        System.out.println("7) Pazar");
        System.out.print("Seciminiz (1-7): ");
        int gun = input.nextInt();

        String gunAdi;
        switch (gun) {
            case 1: gunAdi = "Pazartesi"; break;
            case 2: gunAdi = "Sali"; break;
            case 3: gunAdi = "Carsamba"; break;
            case 4: gunAdi = "Persembe"; break;
            case 5: gunAdi = "Cuma"; break;
            case 6: gunAdi = "Cumartesi"; break;
            case 7: gunAdi = "Pazar"; break;
            default: gunAdi = "Bilinmeyen"; break;
        }

        // SAAT (0–23)
        System.out.print("\nSeans saati (0-23): ");
        int saat = input.nextInt();

        // YAŞ
        System.out.print("\nYasiniz: ");
        int yas = input.nextInt();

        // MESLEK SEÇİMİ 
        System.out.println("\nMeslek secin:");
        System.out.println("1) Ogrenci");
        System.out.println("2) Ogretmen");
        System.out.println("3) Diger");
        System.out.print("Seciminiz (1-3): ");
        int meslekKodu = input.nextInt();

        String meslekAdi;
        switch (meslekKodu) {
            case 1: meslekAdi = "Ogrenci"; break;
            case 2: meslekAdi = "Ogretmen"; break;
            case 3: meslekAdi = "Diger"; break;
            default: meslekAdi = "Bilinmeyen"; break;
        }

        // FILM TÜRÜ SEÇİMİ 
        System.out.println("\nFilm formati secin:");
        System.out.println("1) 2D");
        System.out.println("2) 3D");
        System.out.println("3) IMAX");
        System.out.println("4) 4DX");
        System.out.print("Seciminiz (1-4): ");
        int formatKodu = input.nextInt();

        String formatAdi;
        switch (formatKodu) {
            case 1: formatAdi = "2D"; break;
            case 2: formatAdi = "3D"; break;
            case 3: formatAdi = "IMAX"; break;
            case 4: formatAdi = "4DX"; break;
            default: formatAdi = "Bilinmeyen"; break;
        }

        // HESAPLAMALAR 
        double basePrice   = calculateBasePrice(gun, saat);
        double discountRate = calculateDiscount(yas, meslekKodu, gun);
        double formatExtra = getFormatExtra(formatKodu);
        double finalPrice  = calculateFinalPrice(basePrice, formatExtra, discountRate);

        // ÇIKTI
        generateTicketInfo(
                gunAdi,
                saat,
                yas,
                meslekAdi,
                formatAdi,
                basePrice,
                formatExtra,
                discountRate,
                finalPrice
        );

        input.close();
    }

}

