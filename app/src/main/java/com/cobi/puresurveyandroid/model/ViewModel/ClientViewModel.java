package com.cobi.puresurveyandroid.model.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cobi.puresurveyandroid.model.Birthday;
import com.cobi.puresurveyandroid.model.Campaign;
import com.cobi.puresurveyandroid.model.Commission;
import com.cobi.puresurveyandroid.model.Event;
import com.cobi.puresurveyandroid.model.Lead;
import com.cobi.puresurveyandroid.model.MissedPremium;
import com.cobi.puresurveyandroid.model.AdviserSubmit;
import com.cobi.puresurveyandroid.model.Party;
import com.cobi.puresurveyandroid.model.ReIntermediary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientViewModel extends ViewModel {

    private MutableLiveData<Campaign> selectedCampaign;


    private MutableLiveData<AdviserSubmit> selectOmProtectClients;


    private MutableLiveData<Party> selectedParty;

    public MutableLiveData<Party> getSelectedParty() {
        return selectedParty;
    }

    public void setSelectedParty(Party selectedParty) {
        this.selectedParty = new MutableLiveData<>();
        this.selectedParty.setValue(selectedParty);
    }

    public MutableLiveData<AdviserSubmit> getSelectOmProtectClients() {
        return selectOmProtectClients;
    }

    public void setSelectOmProtectClients(AdviserSubmit selectOmProtectClients) {
        this.selectOmProtectClients = new MutableLiveData<>();
        this.selectOmProtectClients.setValue(selectOmProtectClients);
    }

    private MutableLiveData<List<Lead>> hotLeads;

    public List<Lead> getHotLeads() {
        return hotLeads.getValue();
    }

    public void initHotl(){

        ArrayList<Lead> leads = new ArrayList<>() ;

        hotLeads = new MutableLiveData<>();

        leads.add(new Lead("Mr, DW GOUWS", 112244545, "15 days left to action", "0113925450", "0729228413", "0828259870:", true , 0));
        leads.add(new Lead("Mrs, S Jones", 112524545, "15 days left to action", "01445125450", "5565465", "346546654:" , false, 1));

        hotLeads.setValue(leads);
    }

    public void updateValueHL(int leadIndex, boolean decline){
        if(decline){
            try{
                hotLeads.getValue().remove(leadIndex);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        else{
            hotLeads.getValue().get(leadIndex).setCurrentState("Accepted");
        }
    }

    public void setSelectedCampaign(Campaign item) {
        selectedCampaign = new MutableLiveData<>();
        selectedCampaign.setValue(item);
    }
    public LiveData<Campaign> getSelectedCampaign() {
        return selectedCampaign;
    }






    private MutableLiveData<ReIntermediary> selectedReLead;


    public void setSelectedReLead(ReIntermediary item) {
        selectedReLead = new MutableLiveData<>();
        selectedReLead.setValue(item);
    }

    public LiveData<ReIntermediary> getSelectedReLead() {
        return selectedReLead;
    }


    private MutableLiveData<Lead> selectedLead;

    public void setSelectedLead(Lead item) {
        selectedLead = new MutableLiveData<>();
        selectedLead.setValue(item);
    }

    public LiveData<Lead> getSelectedLead() {
        return selectedLead;
    }





    private  MutableLiveData<MissedPremium> selected;

    public void select(MissedPremium item) {
        selected = new MutableLiveData<>();
        selected.setValue(item);
    }
    public LiveData<MissedPremium> getSelected() {
        return selected;
    }


    public void setPipelineHash(HashMap<String, List<String>> pipelineHash) {
        mPipelineHash = new MutableLiveData<>();
        mPipelineHash.setValue(pipelineHash);
    }

    public MutableLiveData<HashMap<String, List<String>>> getPipelineHash() {
        return mPipelineHash;
    }

    public MutableLiveData<HashMap<String,List<String>>> mPipelineHash;

    public MutableLiveData<Commission> getCurrentCommission() {
        return currentCommission;
    }

    public void setCurrentCommission(Commission commission) {
        currentCommission = new MutableLiveData<>();
        this.currentCommission.setValue(commission);
    }

    private  MutableLiveData<Commission> currentCommission;

    public MutableLiveData<List<MissedPremium>> contractProducts;
    public double getTotalClawback() {
        double clawbackTotal = 0.0F;
        try {
            for (MissedPremium missedPremium : contractProducts.getValue()) {
                if(getSelected().getValue().getContractNumber().equals(missedPremium.getContractNumber())){
                    clawbackTotal += Double.parseDouble(missedPremium.getClawback());
                }
            }
        } catch (Exception e) {
        }
        return clawbackTotal;
    }

    public MutableLiveData<List<Birthday>> birthdays;

    public MutableLiveData<List<Birthday>> getBirthdays() {
        return birthdays;
    }

    public void setBirthdays(List<Birthday> birthdays) {
        this.birthdays = new MutableLiveData<>();
        this.birthdays.setValue(birthdays);
    }

    public MutableLiveData<List<MissedPremium>> getContractProducts() {
        return contractProducts;
    }

    public void setContractProducts(List<MissedPremium> contractProducts) {
        this.contractProducts =  new MutableLiveData<List<MissedPremium>>();
        this.contractProducts.setValue(contractProducts);
    }

    private MutableLiveData<String> contractingParty;

    public String getContractingParty() {
        return contractingParty.getValue();
    }

    public void setContractingParty(String contractingParty) {
        this.contractingParty = new MutableLiveData<>();
        this.contractingParty.setValue(contractingParty);
    }


    //Events

    public MutableLiveData<List<Event>> events;

    public MutableLiveData<List<Event>> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = new MutableLiveData<>();
        this.events.setValue(events);
    }
}
