/**
 * Ad Soyad: Cansude Sayın
 * Öğrenci No: 250541096
 * Proje: RestoranSipariş, Kullanıcının seçtiği yemek, içecek ve tatlılara göre sipariş tutarını hesaplar; combo menü, öğrenci indirimi ve Happy Hour gibi özel durumları değerlendirerek 
 toplam indirim, bahşiş ve ödenecek nihai tutarı belirler.
 * Tarih: 27.11.2025
 */
import java.util.Scanner;

public class RestoranSiparis {

    // Bahsis orani 
    public static final double TIP_RATE = 0.10;

    // === ZORUNLU METOTLAR ===

    // 1) Ana yemek fiyatı
    // 1=Izgara Tavuk, 2=Adana Kebap, 3=Levrek, 4=Mantı, 0=Yok
    public static double getMainDishPrice(int secim) {
        switch (secim) {
            case 1: return 85.0;  // Izgara Tavuk
            case 2: return 120.0; // Adana Kebap
            case 3: return 110.0; // Levrek
            case 4: return 65.0;  // Mantı
            case 0: return 0.0;   // Ana yemek alınmadı
            default: return 0.0;
        }
    }

    // 2) Başlangıç fiyatı
    // 1=Çorba, 2=Humus, 3=Sigara Böreği, 0=Yok
    public static double getAppetizerPrice(int secim) {
        switch (secim) {
            case 1: return 25.0; // Çorba
            case 2: return 45.0; // Humus
            case 3: return 55.0; // Sigara Böreği
            case 0: return 0.0;  // Başlangıç alınmadı
            default: return 0.0;
        }
    }

    // 3) İçecek fiyatı
    // 1=Kola, 2=Ayran, 3=Meyve Suyu, 4=Limonata, 0=Yok
    public static double getDrinkPrice(int secim) {
        switch (secim) {
            case 1: return 15.0; // Kola
            case 2: return 12.0; // Ayran
            case 3: return 35.0; // Meyve Suyu
            case 4: return 25.0; // Limonata
            case 0: return 0.0;  // İçecek yok
            default: return 0.0;
        }
    }

    // 4) Tatlı fiyatı
    // 1=Künefe, 2=Baklava, 3=Sütlaç, 0=Yok
    public static double getDessertPrice(int secim) {
        switch (secim) {
            case 1: return 65.0; // Künefe
            case 2: return 55.0; // Baklava
            case 3: return 35.0; // Sütlaç
            case 0: return 0.0;  // Tatlı yok
            default: return 0.0;
        }
    }

    // 5) Combo mu? (Ana + İçecek + Tatlı varsa true)
    public static boolean isComboOrder(boolean anaVar, boolean icecekVar, boolean tatliVar) {
        return anaVar && icecekVar && tatliVar;
    }

    // 6) Happy Hour mu? (14:00–17:00 arası)
    public static boolean isHappyHour(int saat) {
        return saat >= 14 && saat <= 17;
    }

    // 7) Toplam indirim tutarını hesaplar
    // tutar: toplam hesap
    // combo: combo menü var mı?
    // ogrenci: müşteri öğrenci mi?
    // saat: happy hour için
    public static double calculateDiscount(double tutar, boolean combo, boolean ogrenci, int saat) {
        double discount = 0.0;

        // Combo menü indirim: %15
        if (combo) {
            discount += tutar * 0.15;
        }

        // 200 TL üzeri: %10
        if (tutar > 200.0) {
            discount += tutar * 0.10;
        }

        // Öğrenci (hafta içi olduğu varsayımıyla): %10
        if (ogrenci) {
            discount += tutar * 0.10;
        }

        // Happy Hour: İçeceklerde %20
        if (isHappyHour(saat)) {
            discount += tutar * 0.05;
        }

        return discount;
    }

    // 8) Bahşiş hesaplama (%10)
    public static double calculateServiceTip(double tutar) {
        return tutar * TIP_RATE;
    }

    // Bilet/Fiş formatında çıktı
    public static void printOrderSummary(
            double mainPrice, double appPrice, double drinkPrice, double dessertPrice,
            double subtotal, double discountAmount, double discountedTotal,
            double tip, double finalTotal, boolean combo, boolean ogrenci, int saat
    ) {
        System.out.println("\n===== SIPARIS OZETI =====");
        System.out.printf("Ana Yemek      : %.2f TL%n", mainPrice);
        System.out.printf("Baslangic      : %.2f TL%n", appPrice);
        System.out.printf("Icecek         : %.2f TL%n", drinkPrice);
        System.out.printf("Tatli          : %.2f TL%n", dessertPrice);
        System.out.println("---------------------------");
        System.out.printf("Ara Toplam     : %.2f TL%n", subtotal);

        System.out.println("\n--- Indirimler ---");
        System.out.printf("Toplam Indirim : -%.2f TL%n", discountAmount);
        System.out.printf("Indirimli Tutar: %.2f TL%n", discountedTotal);

        System.out.println("\n--- Bahsis ---");
        System.out.printf("Onerilen Bahsis (%%%d): %.2f TL%n", (int)(TIP_RATE * 100), tip);

        System.out.println("---------------------------");
        System.out.printf("Odenecek Toplam (Bahsis DAHIL): %.2f TL%n", finalTotal);
        System.out.println("===========================\n");
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("=== AKILLI RESTORAN SIPARIS SISTEMI ===");

        // Saat
        System.out.print("Siparis saati (0-23): ");
        int saat = input.nextInt();

        // Öğrenci mi?
        System.out.print("Ogrenci misiniz? (1=Evet, 2=Hayir): ");
        int ogrSecim = input.nextInt();
        boolean ogrenci = (ogrSecim == 1);

        // ANA YEMEK
        System.out.println("\nANA YEMEKLER:");
        System.out.println("1) Izgara Tavuk (85 TL)");
        System.out.println("2) Adana Kebap (120 TL)");
        System.out.println("3) Levrek (110 TL)");
        System.out.println("4) Manti (65 TL)");
        System.out.println("0) Secim yok");
        System.out.print("Seciminiz: ");
        int mainChoice = input.nextInt();

        // BASLANGIC
        System.out.println("\nBASLANGICLAR:");
        System.out.println("1) Corba (25 TL)");
        System.out.println("2) Humus (45 TL)");
        System.out.println("3) Sigara Boregi (55 TL)");
        System.out.println("0) Secim yok");
        System.out.print("Seciminiz: ");
        int appChoice = input.nextInt();

        // ICECEKLER
        System.out.println("\nICECEKLER:");
        System.out.println("1) Kola (15 TL)");
        System.out.println("2) Ayran (12 TL)");
        System.out.println("3) Meyve Suyu (35 TL)");
        System.out.println("4) Limonata (25 TL)");
        System.out.println("0) Secim yok");
        System.out.print("Seciminiz: ");
        int drinkChoice = input.nextInt();

        // TATLILAR
        System.out.println("\nTATLILAR:");
        System.out.println("1) Kunefe (65 TL)");
        System.out.println("2) Baklava (55 TL)");
        System.out.println("3) Sutlac (35 TL)");
        System.out.println("0) Secim yok");
        System.out.print("Seciminiz: ");
        int dessertChoice = input.nextInt();

        // METHOD
        double mainPrice    = getMainDishPrice(mainChoice);
        double appetizerPrice = getAppetizerPrice(appChoice);
        double drinkPrice   = getDrinkPrice(drinkChoice);
        double dessertPrice = getDessertPrice(dessertChoice);

        // Ara toplam
        double subtotal = mainPrice + appetizerPrice + drinkPrice + dessertPrice;

        // Combo kontrolü
        boolean combo = isComboOrder(mainChoice != 0, drinkChoice != 0, dessertChoice != 0);

        // Indirim hesapla
        double discountAmount = calculateDiscount(subtotal, combo, ogrenci, saat);

        double discountedTotal = subtotal - discountAmount;

        // Bahsis
        double tip = calculateServiceTip(discountedTotal);

        double finalTotal = discountedTotal + tip;

        // Özet
        printOrderSummary(
                mainPrice, appetizerPrice, drinkPrice, dessertPrice,
                subtotal, discountAmount, discountedTotal,
                tip, finalTotal, combo, ogrenci, saat
        );

        input.close();
    }
}

