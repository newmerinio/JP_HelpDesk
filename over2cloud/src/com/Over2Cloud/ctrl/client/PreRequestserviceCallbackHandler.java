
/**
 * PreRequestserviceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

    package com.Over2Cloud.ctrl.client;

    /**
     *  PreRequestserviceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class PreRequestserviceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public PreRequestserviceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public PreRequestserviceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for insertIntoTablewithoutquery method
            * override this method for handling normal response from insertIntoTablewithoutquery operation
            */
           public void receiveResultinsertIntoTablewithoutquery(
                    com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTablewithoutqueryResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from insertIntoTablewithoutquery operation
           */
            public void receiveErrorinsertIntoTablewithoutquery(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateTable method
            * override this method for handling normal response from updateTable operation
            */
           public void receiveResultupdateTable(
                    com.Over2Cloud.ctrl.client.PreRequestserviceStub.UpdateTableResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateTable operation
           */
            public void receiveErrorupdateTable(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for insertIntoTable method
            * override this method for handling normal response from insertIntoTable operation
            */
           public void receiveResultinsertIntoTable(
                    com.Over2Cloud.ctrl.client.PreRequestserviceStub.InsertIntoTableResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from insertIntoTable operation
           */
            public void receiveErrorinsertIntoTable(java.lang.Exception e) {
            }
                


    }
    