#Mobile Payments Workshop

This is project-tutorial of the **Mobile Payments Workshop for Android** using [Braintree v.zero](https://www.braintreepayments.com/v.zero) and [DropIn](https://developers.braintreepayments.com/android+php/guides/drop-in).

Each step in this project-tutorial is a branch. You can clone, checkout step1 and following the instructions to do the exercises. If you want to compare your code with the project-tutorial in case of questions or copy the code from the tutorial to finish a step, just checkout the desired branch.

This project-tutorial does not cover the server-side part. To see server-side code used in this tutorial please look at my [Battlehack Demo](https://github.com/jeffprestes/bhdemo) where I have implemented it using PHP.

To follow this tutorial you must have installed [Android Studio](https://guides.cocoapods.org/using/getting-started.html). 

If you want more details about Braintree Android SDK to do the exercises, go to [Braintree Documentation](https://developers.braintreepayments.com/android+php/start/hello-client).

  
##List of content

* [Step 1](https://github.com/jeffprestes/AndroidVZeroDemo/tree/step1) - New Project

* [Step 2](https://github.com/jeffprestes/AndroidVZeroDemo/tree/step2) - Design your page and define your App Icon. Leave the Buy button disabled. It will be enabled when the App gets the Braintree Token from the Server Side.


* [Step 3](https://github.com/jeffprestes/AndroidVZeroDemo/tree/step3) - At **build.gradle** configure the dependencies. Add Braintree's dependency for compile ```'com.braintreepayments.api:braintree:1.+'```. Also, as you are going to need to make HTTP calls to your Server-Side, add your preferred library. In this tutorial we used [Android Asynchronous Http Client](loopj.com/android-async-http/) but you can select another one if you like.


* [Step 4](https://github.com/jeffprestes/AndroidVZeroDemo/tree/step4) - In **AndroidManifest.xml**, asks permissions to *INTERNET* and *ACCESS_NETWORK_STATE*. Also, add  DropIn activity as ```<activity android:name="com.braintreepayments.api.dropin.BraintreePaymentActivity" />```.


* [Step 5](https://github.com/jeffprestes/AndroidVZeroDemo/tree/step5) - At your Activity class, in this tutorial it's the **BraintreeDemo.java**, create a method to call Server-Side to get *clientToken*, if the call is successfully enable the '*Buy*' button. In this tutorial this method is called on *onCreate*.


* [Step 6](https://github.com/jeffprestes/AndroidVZeroDemo/tree/step6) - Continuing at your Activity, create a method to be called by the *Buy* button (don’t forget to include it at Xml Layout file, in our case **activity_braintree_demo.xml**) and in there pass the purchase parameters to *BraintreePaymentActivity* using CustomizationBuilder class. **Important**: you must include clientToken as extra into the Intent too. ```intent.putExtra(BraintreePaymentActivity.EXTRA_CLIENT_TOKEN, clientToken);``. After this step concluded, if you run the App, you will be able to see the DropIn.


* [Step 7](https://github.com/jeffprestes/AndroidVZeroDemo/tree/step7) - At your Activity, implement *onActivityResult* method to check the status of the *DropIn's* return. And if it’s ok, create and call a method will send to the Server-Side the *Nonce* generated by the DropIn. With *Nonce* the *Server-Side* will be able to call *Braintree* to process the transaction. If the transaction is *succeed* send an alert to the buyer, get another Token and change the button description to ‘*Buy it again!*"





Done.
Now you have an App with Mobile Payment enabled.

Any questions, please email me: <jefferson.prestes@braintreepayments.com>