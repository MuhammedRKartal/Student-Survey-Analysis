import java.util.ArrayList;

public class File_f {
    private String name;
    private String ÖğretimElemanı;
    private String DersKodu;
    private int ÖğrenciSayısı;
    private int sect;//Section Numeric value


    private String CevaplamaOranı;
    private int    CevapAdedi;
    public int yaer;
    public ArrayList<String> comments=new ArrayList<>();
     Section section[]=new Section[8];

    public void setSect(int sect) {
        this.sect = sect;
    }
    public int getSect() {
        return sect;
    }

    public int getÖğrenciSayısı() {
        return ÖğrenciSayısı;
    }

    public void setÖğrenciSayısı(int öğrenciSayısı) {
        ÖğrenciSayısı = öğrenciSayısı;
    }

    public File_f() {
        for (int i=0;i<6;i++){
            section[i]=new Section();
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setÖğretimElemanı(String öğretimElemanı) {
        ÖğretimElemanı = öğretimElemanı;
    }

    public void setDersKodu(String dersKoduSection) {
        DersKodu = dersKoduSection;
    }


    public void setCevaplamaOranı(String cevaplamaOranı) {
        CevaplamaOranı = cevaplamaOranı;
    }

    public void setCevapAdedi(int cevapAdedi) {
        CevapAdedi = cevapAdedi;
    }

    public String getName() {
        return name;
    }

    public String getÖğretimElemanı() {
        return ÖğretimElemanı;
    }

    public String getDersKodu() {
        return DersKodu;
    }



    public String getCevaplamaOranı() {
        return CevaplamaOranı;
    }

    public int getCevapAdedi() {
        return CevapAdedi;
    }
    public void addSection(String name,int x){
        section[x]=new Section();
        section[x].setText(name);


    }
}
