package org.xms.g.measurement;

/**
 * This class is deprecated. This receiver is no longer used for Install Referrers.<br/>
 * HMS api does not provide in this Class. More details about the related GMS api can be seenin the reference below.<br/>
 * com.google.android.gms.measurement.AppMeasurementInstallReferrerReceiver: This class is deprecated. This receiver is no longer used for Install Referrers.<br/>
 */
public final class AppMeasurementInstallReferrerReceiver extends android.content.BroadcastReceiver implements org.xms.g.utils.XGettable {
    public java.lang.Object gInstance;
    public java.lang.Object hInstance;
    
    /**
     * org.xms.g.measurement.AppMeasurementInstallReferrerReceiver.AppMeasurementInstallReferrerReceiver(org.xms.g.utils.XBox) constructor of AppMeasurementInstallReferrerReceiver with XBox.<br/>
     * Support running environments including both HMS and GMS which are chosen by users.<br/>
     * Below are the references of HMS apis and GMS apis respectively:<br/>
     *
     * @param param0 the wrapper of xms instance
     */
    public AppMeasurementInstallReferrerReceiver(org.xms.g.utils.XBox param0) {
        if (param0 == null) {
            return;
        }
        this.setGInstance(param0.getGInstance());
        this.setHInstance(param0.getHInstance());
    }
    
    /**
     * org.xms.g.measurement.AppMeasurementInstallReferrerReceiver.AppMeasurementInstallReferrerReceiver() constructor of AppMeasurementInstallReferrerReceiver.<br/>
     * Support running environments including both HMS and GMS which are chosen by users.<br/>
     * Below are the references of HMS apis and GMS apis respectively:<br/>
     * com.google.android.gms.measurement.AppMeasurementInstallReferrerReceiver.AppMeasurementInstallReferrerReceiver(): <a href="https://developers.google.com/android/reference/com/google/android/gms/measurement/AppMeasurementInstallReferrerReceiver#public-appmeasurementinstallreferrerreceiver">https://developers.google.com/android/reference/com/google/android/gms/measurement/AppMeasurementInstallReferrerReceiver#public-appmeasurementinstallreferrerreceiver</a><br/>
     *
     */
    public AppMeasurementInstallReferrerReceiver() {
    }
    
    /**
     * XMS does not provide this api.<br/>
     */
    public final android.content.BroadcastReceiver.PendingResult doGoAsync() {
        throw new java.lang.RuntimeException("Not Supported");
    }
    
    /**
     * XMS does not provide this api.<br/>
     */
    public final void doStartService(android.content.Context param0, android.content.Intent param1) {
        throw new java.lang.RuntimeException("Not Supported");
    }
    
    /**
     * XMS does not provide this api.<br/>
     */
    public final void onReceive(android.content.Context param0, android.content.Intent param1) {
        throw new java.lang.RuntimeException("Not Supported");
    }
    
    /**
     * org.xms.g.measurement.AppMeasurementInstallReferrerReceiver.setGInstance(java.lang.Object) set the gms instance for the corresponding xms instance.<br/>
     * Support running environments including both HMS and GMS which are chosen by users.<br/>
     * Below are the references of HMS apis and GMS apis respectively:<br/>
     *
     * @param param0 the instance of gms
     */
    public void setGInstance(java.lang.Object param0) {
        this.gInstance = param0;
    }
    
    /**
     * org.xms.g.measurement.AppMeasurementInstallReferrerReceiver.setHInstance(java.lang.Object) set the hms instance for the corresponding xms instance.<br/>
     * Support running environments including both HMS and GMS which are chosen by users.<br/>
     * Below are the references of HMS apis and GMS apis respectively:<br/>
     *
     * @param param0 the instance of hms
     */
    public void setHInstance(java.lang.Object param0) {
        this.hInstance = param0;
    }
    
    /**
     * org.xms.g.measurement.AppMeasurementInstallReferrerReceiver.getGInstance() get the gms instance from the corresponding xms instance.<br/>
     * Support running environments including both HMS and GMS which are chosen by users.<br/>
     * Below are the references of HMS apis and GMS apis respectively:<br/>
     *
     * @return the instance of gms
     */
    public java.lang.Object getGInstance() {
        return this.gInstance;
    }
    
    /**
     * org.xms.g.measurement.AppMeasurementInstallReferrerReceiver.getHInstance() get the hms instance from the corresponding xms instance.<br/>
     * Support running environments including both HMS and GMS which are chosen by users.<br/>
     * Below are the references of HMS apis and GMS apis respectively:<br/>
     *
     * @return the instance of hms
     */
    public java.lang.Object getHInstance() {
        return this.hInstance;
    }
    
    /**
     * org.xms.g.measurement.AppMeasurementInstallReferrerReceiver.dynamicCast(java.lang.Object) dynamic cast the input object to org.xms.g.measurement.AppMeasurementInstallReferrerReceiver.<br/>
     * Support running environments including both HMS and GMS which are chosen by users.<br/>
     *
     * @param param0 the input object
     * @return casted AppMeasurementInstallReferrerReceiver object
     */
    public static org.xms.g.measurement.AppMeasurementInstallReferrerReceiver dynamicCast(java.lang.Object param0) {
        throw new java.lang.RuntimeException("Not Supported");
    }
    
    /**
     * org.xms.g.measurement.AppMeasurementInstallReferrerReceiver.isInstance(java.lang.Object) judge whether the Object is XMS instance or not.<br/>
     * Support running environments including both HMS and GMS which are chosen by users.<br/>
     *
     * @param param0 the input object
     * @return true if the Object is XMS instance, otherwise false
     */
    public static boolean isInstance(java.lang.Object param0) {
        throw new java.lang.RuntimeException("Not Supported");
    }
}