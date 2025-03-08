package manager;

import utils.AppSettigs;

public class AppManager {
    private AppSettigs as;
    private KlijentManager klijentMng;
    private MenadzerManager menadzerMng;
    private RecepcionerManager recepcionerMng;
    private KozmeticarManager kozmeticarMng;
    private CenovnikManager cenovnikMng;
    private VrstaTretmanaManager vrstaTretmanaMng;
    private TretmanManager tretmanMng;
    private TipTretmanaManager tipTretmanaMng;
    private SalonInfoManager saloninfoMng;

    public AppManager(AppSettigs as){
        this.as = as;
        this.klijentMng = new KlijentManager(this.as.getKlijenti());
        this.menadzerMng = new MenadzerManager(this.as.getMenadzeri());
        this.recepcionerMng = new RecepcionerManager(this.as.getRecepcioneri());
        this.kozmeticarMng = new KozmeticarManager(this.as.getKozmeticari());
        this.cenovnikMng = new CenovnikManager(this.as.getCenovnik());
        this.vrstaTretmanaMng = new VrstaTretmanaManager(this.as.getVrsteTretmana());
        this.tretmanMng = new TretmanManager(this.as.getTretmani(),vrstaTretmanaMng,kozmeticarMng);
        this.tipTretmanaMng = new TipTretmanaManager(this.as.getTipoviTretmana());
        this.saloninfoMng = new SalonInfoManager(this.as.getSalonInfo());
    }

    public KlijentManager getKlijentMng(){ return klijentMng; }
    public MenadzerManager getMenadzerMng(){return menadzerMng; }
    public RecepcionerManager getRecepcionerMng(){ return recepcionerMng;}
    public KozmeticarManager getKozmeticarMng(){ return kozmeticarMng;}
    public CenovnikManager getCenovnikMng(){ return cenovnikMng;}
    public VrstaTretmanaManager getVrstaTretmanaMng(){ return vrstaTretmanaMng;}
    public TretmanManager getTretmanMng(){ return tretmanMng;}
    public TipTretmanaManager getTipTretmanaMng(){ return tipTretmanaMng; }
    public SalonInfoManager getSalonInfoMng(){ return saloninfoMng;}

    public void loadData(){
        this.klijentMng.loadData();
        this.menadzerMng.loadData();
        this.recepcionerMng.loadData();
        this.kozmeticarMng.loadData();
        this.cenovnikMng.loadData();
        this.vrstaTretmanaMng.loadData();
        this.tretmanMng.loadData();
        this.tipTretmanaMng.loadData();
        this.saloninfoMng.loadData();
    }

}
