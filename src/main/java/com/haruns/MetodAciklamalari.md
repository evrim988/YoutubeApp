### 1-UserRepository
- Boolean isExistUsername(String username): Parametreye girilen username değeri eğer databasede kayıtlı ise true değerini
değil ise false değerini döner.

- boolean isExistEmail(String email): Parametreye girilen mail değeri eğer databasede kayıtlı ise true değerini
  değil ise false değerini döner.

- boolean isUsernameAndMailExist(String username, String email): Girilen username ve email String değer çiftleri ile databasede
eşleşen kayıt varsa true, yoksa false döner.

- Optional<User> findByUsername(String username): Girilen String değer ile eşleşen nesne databede kayıtlı var ise
Optional olarak o useri döner, yoksa Optional.empty() döner.

- Optional<User> findByUsernameAndPassword(String username, String password)
Kullanıcı girişi kontrolü için username ve password doğruluğunu kontrol eden metod. Eğer
username ve password ile eşleşen kayıtlı User varsa geri o Useri Optinal olarak döner.

- List< Video > getLikedVideosOfUser(User user): Kullanıcının beğendiği videoları liste olarak geri döner.
Eğer hiç beğendiği video yoksa boş liste döner.

List< Comment > getAllCommentsOfUser(User user): Kullanıcının videolara yapmış olduğu bütün
yorumları liste olarak getirir. Yoksa boş liste geri döner.

### 2-VideoRepository
- List< Video > getTrendVideos(): En çok izlenen 5 videoyu liste olarak döner, hiç video kayıtı yok ise
boş liste döner.
- findVideosByTitle(String baslik): Dışarıdan girilen parametreyi içinde barındıran başlıklar var ise o başlıkların ait olduğu
videoları liste olarak geri döner. 
- List< Video > findVideosOfUser(User user): Kullanıcının paylaşmış olduğu tüm videoları liste olarak geri döner.
- void goruntulenmeArttir(Video video): Bir video menüden izlendikten sonra o videonun izlenmesini
1 arttırıp databasede video nesnesini günceller.

### 3-CommentRepository
- List< Comment > findCommentOfVideo(Long video_id): video_id ile eşleşen videoya ait bütün yorumları
liste olarak geri döner.
- Long countCommentsOfVideo(Long video_id): Videoya atılan toplam yorum sayısını geri Long olarak döner.

### 4-LikeRepository
-boolean isLikeExist(Long user_id,Long video_id): Kullanıcı menüden bir videoya like işlemleri yapmadan önce
o kullanıcının aynı videoya daha önceden like atıp atmadığını kontrol eder. Bu metodun sonucuna göre kullanıcıya
gösterilecek Beğen menüsü değişikliğe uğrar.
-Like findByUserIdAndVideoId(Long user_id,Long video_id): Eğer kullanıcı izlediği videoya
daha önce like ya da dislike attıysa, o Like kaydını bulup geri döner.
-Long countLikes(Long video_id): Videoya atılan toplam like sayısını geri döner.
-Long countDislikes(Long video_id): Videoya atılan toplam dislike sayısını geri döner.

### 5-CommentGarbageRepository
- List < CommentGarbage > findAllOldComments(Long comment_id):
Kullanıcının atmış olduğu yorumun düzeltilmeden önceki hallerinin listesini geri döner.

### 6-MainGUI

- void mainGUI(): Runnerden ana menümüzü başlatmaya yarayan metod.
- int mainMenu(): Ana menü seçeneklerini kullanıcıya sunup, seçime göre int değer döndürür.
- void mainMenuOptions(int secim): Parametre olarak secimi mainMenu() metodundan alır. O seçime göre içeride yazılan switch caselerinden
birini yapar. Eğer kullanıcı çıkış seçeneğini seçmediyse tekrar ana menüye geri dönecek şekilde ayarlandı.

### 7-UserGUI
- void userModule(): UserGUI kullanıcı menüsünü çağırır. MainGUI'de mainMenuOptions() seçeneklerinden birisi bu metodu çağırığ kullanıcı menüsüne giriş sağlanır.
- int userLoginMenu(): Giriş Yap ve Geri dön olmak üzere kullanıcıya iki adet seçim sunup, kullanıcının yapmış olduğu seçimi int olarak geri döner.
- int userLoginMenuOptions(int secim): userLoginMenu()'den dönen değere göre içerideki switch yapısını çalıştırır. Kullanıcı geri dön demediği sürece kendini çağırmaya
  devam eder.
- int userMenu(): Kullanıcı başarılı bir şekilde giriş yaptıysa, Kullanıcı Menüsü seçeneklerini gösterir, seçeneği int değer olarak geri döner.
- void userMenuOptions(int secim): userMenu()'den dönen int secim sonucuna göre switch yapısını çalıştırır.
- User findUser(): Dışarıdan (Fronttan) girilen kullanıcı ismi ile arama yapar. Eğer kullanıcı bulunamazsa null döner.
- int videoIzle(): Bir videoyu izle dedikten sonra açılır, kullanıcıya beğenMenüsü, yorumYap, videoYorumlarınıGöster gibi seçenekler sunulur. Kullanıcı seçimini
  int olarak geri döner.
- void videoIzleOptions(int secim): videoIzle()'den gelen int değerine göre switch yapısını çalıştırır.
- Video vidoSec(List< Video > videoList): Kullanıcının aramasına göre gelen videoListesinden tek bir videoyu seçmesini sağlar. Seçilen videoyu geri döner.
- Video goruntulenmeArttir(List< Video > videoList): videoSec() metodunan dönen Video nesnesi null değill ise videonun görüntülenmesini 1 arttırır. 
- void begen(User user,Video video): Beğen menüsü getirir. Burda kullanıcının daha önce like ya da dislike atıp atmadığı ya da like veya
- dislikenini geri çekip çekmediğine bağlı olarak farklı menüler getirip kullanıcıya seçim yaptırır.
- int userProfileMenu(): Kullanıcının youtube kanalı gibi çalışması hedeflendi. Bilgi güncelleme, beğendiyi videoları gösterme gibi seçenekleri
  menü olarak sunup kullanıcının yaptığı seçimi geri int döner.
- int userProfileMenuOptions(int secim): userProfileMenu()'de yapılan secime göre switch yapısını çalıştırır.
- void yorumDuzenle(): Kullanıcı daha önceden yaptığı yorumu aratıp bulduğunda, o yorumu düzenler. Düzelttiği yorumu databasede
  update etmeden önce eski yorum CommentGarbage sınıfı vasıtasıyla databasede kayıtta tutulur daha sonra güncel yorum update edilir.
- void yorumYap(): Inputtan gelen yorum, databaseye kaydedilir.
- void videoSil(): Kullanıcı kendi yüklediği videolardan seçim yapıp o videoyu HARD DELETE olarak siler.
- void videoEkle(): Kanalına video eklemek isteyen kullanıcı video bilgilerini girdikten sonra databaseye kaydedilir.
- User doLogin(): Inputtan girilen username ve password değerlerini alır. Eğer databasede eşleşme var ise o useri değil ise null döner.
- User register(): Kullanıcı kayıt bilgilerini alıp databaseye User olarak kaydeder.
- void sifreDegistir(): Kullanıcı şifresini değiştirmesini sağlar.
- void videoTitleDegistir(): Kullanıcının yüklediği videonun başlığını değiştirmesini sağlar.
- void videoAciklamaDegistir(): Kullanıcının yüklediği videonun açıklamasını değiştirmesini sağlar.
- Comment yorumSec(List< Comment > commentList): Yorum listesinden tek bir yorumun seçilmesini sağlayıp o yorumu geri döndürür.

### 8-UserModel
- Youtube Kanalım gibi oluşturulmaya çalışıldı. Kullanıcı ismi, yüklediği videolar, kullanıcının isim-soyismi,maili 
  en çok izlenen 5 videoyu izlenme sayılarıyla birlikte gösterir.

### 9-VideoModel
- Bu modelde öncelikle görsel olarak Bir video karesi oluşturuldu. Videonun toplam izlenmesi, beğenileri, dislikeları
 video karesinin hemen altında gösterilecek şekilde hizalandı. Bunların altında video açıklaması tek satır olarak, onun
 altında da 5 adet en güncel yorumlar gösteriliyor. Bunlardan sonra kullanıcıya daha fazla yorum görüp görmek istemediği,
 açıklamanın tamamını okumak isteyip istemediği soruluyor. Kullanıcı seçimine göre model tekrar güncel halinde gösterilir ya 
 da kullanıcı çıkış yapmak isterse devam edilir.
