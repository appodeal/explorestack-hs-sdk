# Appodeal SDK Connector for Holistic Solution SDK

## 1. Integrate Appodeal SDK

Please, follow this [guide](https://wiki.appodeal.com/display/DE/Android+SDK.+Integration+Guide) for integrating Appodeal SDK to your app. 

If the Appodeal SDK is already integrated, you can skip this step and go to step 2.

## 2. Import Connector

```groovy
dependencies {
    // ... other project dependencies

    implementation 'com.explorestack.hs.sdk.connector:appodeal:1.0.1'
}
```

## 3. Register Connector

```java
public class YourApplication extends Application {
    ...
    @Override
    public void onCreate() {
        super.onCreate();

        //Create connector for Appodeal
        HSAppodealConnector appodealConnector = new HSAppodealConnector();
    
        //Create HSApp configuration
        HSAppConfig appConfig = new HSAppConfig()
                //Include Appodeal connector to HSApp config
                .withConnectors(appodealConnector);
    }
    ...
}
```

[appodeal_sdk_initialization]: appodeal_sdk_initialization

## 4. Initialize the Appodeal SDK

First, make sure you've read how to configure and initialize [HSApp](../../README.md#initialize-sdk)

```java
public class ExampleActivity extends AppCompatActivity {

    private static final String TAG = ExampleActivity.class.getSimpleName();

    private final HSAppInitializeListener hsAppInitializeListener = new HSAppInitializeListener() {
        @Override
        public void onAppInitialized(@Nullable List<HSError> errors) {
            if (errors != null) {
                for (HSError error : errors) {
                    Log.e(TAG, "HSApp: [Error]: " + error.toString());
                }
            }
            // HSApp was successfully initialized and now you can initialize Appodeal SDK
            initializeAppodeal(ExampleActivity.this);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check, if HSApp is initialized. If 'yes', then you can initialize appropriate SDK,
        // otherwise - subscribe to listen to the initialization state
        if (HSApp.isInitialized()) {
            // HSApp was successfully initialized and now you can initialize required SDK
            initializeAppodeal(this);
        } else {
            // Attach HSApp initialization listener
            HSApp.addInitializeListener(hsAppInitializeListener);
            // Start HSApp initialization if it's not started yet
            ExampleApplication.initializeHSApp(getApplicationContext());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove HSApp initialize listener, since HSApp store strong reference to provided listener
        HSApp.removeInitializeListener(hsAppInitializeListener);
    }

    /**
     * Storing and processing Appodeal SDK initialization
     */
    private static boolean isAppodealInitialized = false;

    private static void initializeAppodeal(@NonNull Activity activity) {
        if (!isAppodealInitialized) {
            Appodeal.initialize(activity, YOUR_APPODEAL_KEY, REQUIRED_ADS_TYPES);
            isAppodealInitialized = true;
        }
    }
}
```

[Code example](../../example/src/main/java/com/explorestack/hs/sdk/example/ExampleActivity.java#L19)
