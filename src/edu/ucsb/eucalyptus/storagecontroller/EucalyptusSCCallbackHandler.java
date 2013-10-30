
/**
 * EucalyptusSCCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package edu.ucsb.eucalyptus.storagecontroller;

    /**
     *  EucalyptusSCCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class EucalyptusSCCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public EucalyptusSCCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public EucalyptusSCCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for unexportVolume method
            * override this method for handling normal response from unexportVolume operation
            */
           public void receiveResultunexportVolume(
                    edu.ucsb.eucalyptus.storagecontroller.EucalyptusSCStub.UnexportVolumeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from unexportVolume operation
           */
            public void receiveErrorunexportVolume(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for exportVolume method
            * override this method for handling normal response from exportVolume operation
            */
           public void receiveResultexportVolume(
                    edu.ucsb.eucalyptus.storagecontroller.EucalyptusSCStub.ExportVolumeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from exportVolume operation
           */
            public void receiveErrorexportVolume(java.lang.Exception e) {
            }
                


    }
    