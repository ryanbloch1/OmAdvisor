package com.cobi.puresurveyandroid.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.adapter.OmpPartyRowAdapter;
import com.cobi.puresurveyandroid.adapter.OutstandingRequirementsAdapter;
import com.cobi.puresurveyandroid.databinding.OmpProtectContractBinding;
import com.cobi.puresurveyandroid.databinding.PopupContactOmpBinding;
import com.cobi.puresurveyandroid.databinding.PopupContactReinterBinding;
import com.cobi.puresurveyandroid.databinding.PopupOrBinding;
import com.cobi.puresurveyandroid.model.AdminRequirement;
import com.cobi.puresurveyandroid.model.ContactPoint;
import com.cobi.puresurveyandroid.model.Customer;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.OmpORRequest;
import com.cobi.puresurveyandroid.model.OmpOutstandingRequirements;
import com.cobi.puresurveyandroid.model.OmpReqStrings;
import com.cobi.puresurveyandroid.model.OutstandingReqParty;
import com.cobi.puresurveyandroid.model.OutstandingRequirementRoleToClient;
import com.cobi.puresurveyandroid.model.AdviserSubmit;
import com.cobi.puresurveyandroid.model.Party;
import com.cobi.puresurveyandroid.model.RoleOmp;
import com.cobi.puresurveyandroid.model.RolesEnum;
import com.cobi.puresurveyandroid.model.UwRequirement;
import com.cobi.puresurveyandroid.model.ViewModel.ClientViewModel;
import com.cobi.puresurveyandroid.util.DateHelper;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.util.ResourceHelper;
import com.cobi.puresurveyandroid.webservice.controller.CSIApiController;
import com.cobi.puresurveyandroid.webservice.controller.OmpController;
import com.cobi.puresurveyandroid.webservice.controller.ReinterApiController;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;



public class OmpContractFragment extends BaseFragment implements OmpPartyRowAdapter.OnClientSelected {

    private OmpProtectContractBinding mBinding;
    private ClientViewModel clientViewModel;
    private PopupOrBinding popupBinding = null;
    String tempUserRole = "";

    String tempBancsKey = "";

    String tempOmpName = "";

    private boolean imageBool = false;

    PopupContactOmpBinding contactBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.omp_protect_contract, container, false);

        clientViewModel = ViewModelProviders.of(getActivity()).get(ClientViewModel.class);

        AdviserSubmit obj = clientViewModel.getSelectOmProtectClients().getValue();
        mBinding.setData(obj);

        Date currentDate = new Date(System.currentTimeMillis());

        mBinding.setCurrentDate(DateHelper.dateToSimpleDate(currentDate));

        popupBinding = DataBindingUtil.inflate(inflater, R.layout.popup_or, container, false);

        mBinding.contactDet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showContactDetails(container);

            }
        });
        List<OutstandingRequirementRoleToClient> adapterList = new ArrayList<>();

        List<String> roles = new ArrayList<>();

        //first create  list of total roles
        for(Party p : obj.getParty()){
            for(String role : p.getRoles()){

                roles.add(role);

            }
        }

        List<String> uniqueRoleList = new ArrayList<>();
        //remove duplicates from roles list

        for(String role : roles){
            if(!uniqueRoleList.contains(role)){
                uniqueRoleList.add(role);
            }
        }


        List<RoleOmp> sortedRoleList = new ArrayList<>();


        for(String r : uniqueRoleList){

            if(r.equals("POLICY OWNER")){
                sortedRoleList.add(new RoleOmp("POLICY OWNER", RolesEnum.POLICYOWNER));
            }
            if(r.equals("PREMIUM PAYER")){
                sortedRoleList.add(new RoleOmp("PREMIUM PAYER", RolesEnum.PREMIUMPAYER));

            }
           if(r.equals("INSURED PERSON")){
               sortedRoleList.add(new RoleOmp("INSURED PERSON", RolesEnum.INSUREDPERSON));

            }

            if(r.equals("BUSINESS ENTITY")){
                sortedRoleList.add(new RoleOmp("BUSINESS ENTITY", RolesEnum.BUSINESSENTITY));

            }








        }

        //sort the role list here..

        Collections.sort(sortedRoleList);

        List<String> testSort = new ArrayList<>();


        for(RoleOmp p : sortedRoleList){
            testSort.add(p.getRole());

        }



        for(String uniqueRole : testSort){

            List<Party> clientRoleList = new ArrayList<>();

            for(Party p : obj.getParty()){

                for(String role : p.getRoles()){ //look for role in party to see if it contains the unique role

                    if(role.equals(uniqueRole)){


                        if(p.getLastName() != null && p.getFirstName() !=  null){
                            if(role.equals("POLICY OWNER")){
                                if(p == clientViewModel.getSelectedParty().getValue()){
                                    p.setSelectedRole(uniqueRole);


                                    clientRoleList.add(p);
                                }
                            }else{
                                p.setSelectedRole(uniqueRole);


                                clientRoleList.add(p);

                            }



                        }

                        continue;


                    }
                }

            }

            adapterList.add(new OutstandingRequirementRoleToClient(uniqueRole, clientRoleList));







        }



        OutstandingRequirementsAdapter adapter= new OutstandingRequirementsAdapter(adapterList, getContext(), this);
        mBinding.outstandingReqList.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mBinding.outstandingReqList.setHasFixedSize(true);
        mBinding.outstandingReqList.setAdapter(adapter);


        return mBinding.getRoot();

    }


    private void showContactDetails(ViewGroup container) {


        Party selectedPClient = clientViewModel.getSelectedParty().getValue();


        final Dialog dialog = new Dialog(getContext(), R.style.DialogTheme);

        LayoutInflater inflater = LayoutInflater.from(getContext());

        contactBinding = DataBindingUtil.inflate(inflater, R.layout.popup_contact_omp, container, false);


        List<ContactPoint> numberList = selectedPClient.getContactPoints();

        String cellNumber = "";
         String landline = "";

        for(ContactPoint cp : numberList){

            if(cp.getPhoneType().contains("MOBILE")){

                cellNumber = cp.getPhoneNumber();
            }


            if(cp.getPhoneType().contains("LANDLINE")){

                landline = cp.getPhoneNumber();
            }




        }


        contactBinding.setData(new Customer("", "", selectedPClient.getFirstName(), selectedPClient.getLastName(), cellNumber, "",landline, !selectedPClient.getElectronicAddresses().isEmpty() ? selectedPClient.getElectronicAddresses().get(0).getEmailId() : "" ));

        contactBinding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        contactBinding.layoutSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSms(contactBinding.cell.getText().toString(), false, null);
            }
        });

        contactBinding.whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWhatsappConversation(contactBinding.cell.getText().toString(), "");

            }
        });


        contactBinding.homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactBinding.landline.getText().toString();            }
        });

        contactBinding.cellLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall(contactBinding.cell.getText().toString(), null);
            }
        });

        contactBinding.emailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!selectedPClient.getElectronicAddresses().isEmpty()){
                    sendEmail(selectedPClient.getElectronicAddresses().get(0).getEmailId(), false, null);

                }
            }
        });

        contactBinding.layoutEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!selectedPClient.getElectronicAddresses().isEmpty()){
                    sendEmail(selectedPClient.getElectronicAddresses().get(0).getEmailId(), false, null);

                }

            }
        });


        dialog.setCancelable(false);

        dialog.getWindow().getDecorView().setBackground(new ColorDrawable(Color.TRANSPARENT));

        dialog.setContentView(contactBinding.getRoot());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        }, 1000);


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(OmpOutstandingRequirements result) {

        mBinding.progress.setVisibility(View.GONE);
        popupBinding.progress.setVisibility(View.GONE);

        if (result instanceof OmpOutstandingRequirements) {

            OutstandingReqParty client = null;

            if (result.getParties() != null &&!result.getParties().isEmpty()) {
                for (OutstandingReqParty p : result.getParties()) {

                    if (p.getBancsBpKey().equals(tempBancsKey)) {

                        client = p;


                        break;
                    }
                }
            }

            String top = tempOmpName + "\n\n" + tempUserRole;

            if (client != null) {

                //create popup with data from client..
                String uwReq = "";

                String adminReq = "";

                if(!client.getUwRequirements().isEmpty()){
                    for (UwRequirement uwr : client.getUwRequirements()) {

                        uwReq = uwReq + uwr.getName() + "\n\n";

                    }

                }else{
                    uwReq = "No outstanding requirements";

                }

                if(!client.getAdminRequirements().isEmpty()){
                    for (AdminRequirement req : client.getAdminRequirements()) {

                        adminReq = adminReq + req.getName() + "\n\n";

                    }
                }else{
                    adminReq = "No outstanding requirements";

                }
                popupBinding.setData(new OmpReqStrings(top, adminReq, uwReq));
            }
            else{

                popupBinding.setData(new OmpReqStrings(top, "No outstanding requirements", "No outstanding requirements"));

            }
        }


    }

    @Override
    public void OnClientSelected(Party p, String role) {


        //api call..

        popupBinding.error.setVisibility(View.GONE);


        final Dialog dialog = new Dialog(getContext(), R.style.DialogTheme);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        RelativeLayout mView = (RelativeLayout) inflater.inflate(R.layout.omp_or_layout, null);

        ImageView close = mView.findViewById(R.id.close);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if (popupBinding != null) {


            if (popupBinding.getRoot().getParent() != null) {
                ((ViewGroup) popupBinding.getRoot().getParent()).removeView(popupBinding.getRoot()); // <- fix
            }

            mView.addView(popupBinding.getRoot());
        }

        dialog.setCancelable(false);

        dialog.getWindow().getDecorView().setBackground(new ColorDrawable(Color.TRANSPARENT));

        dialog.setContentView(mView);

        popupBinding.setData(new OmpReqStrings("", "", ""));

        popupBinding.progress.setVisibility(View.VISIBLE);

        tempBancsKey = p.getBancsBpKey();

        tempOmpName = p.getLastName() + ", " + p.getFirstName();

        tempUserRole = role;

        OmpController.getOutstandingRequirements(getContext(), new OmpORRequest(clientViewModel.getSelectOmProtectClients().getValue().getApplicationInstance()));

        //show popup stuff...
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        }, 500);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {
        mBinding.progress.setVisibility(View.GONE);

        popupBinding.progress.setVisibility(View.GONE);

        //Dont show errors, just show 0
        if (errorResponse.getRequest().equals(OmpController.CUSTOMER_GET_OR)) {
            //show error message....

            popupBinding.error.setVisibility(View.VISIBLE);
        }

    }

}
