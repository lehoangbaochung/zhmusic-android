package com.zitherharp.zhmusic.provider;

public class SheetProvider {
//    static final String APPLICATION_NAME = "Google Sheet";
//   // static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
//    static final String TOKENS_DIRECTORY_PATH = "tokens";
//    static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
//    static final String CREDENTIALS_FILE_PATH = "/credentials.json";
//
//    static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
//        Log.e("Init sheet:", "sheet");
//        InputStream in = SheetProvider.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
//        if (in == null) {
//            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
//        }
//        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
//// loi
//        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
//                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
//                .setAccessType("offline")
//                .build();
//
//        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
//        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
//    }
//
//    public static ArrayList getOnlineSongs() throws IOException, GeneralSecurityException {
//        Log.e("Start sheet:", "readSheet");
//        ArrayList songs = new ArrayList<Song>();
//        // Build a new authorized API client service.
//        final NetHttpTransport HTTP_TRANSPORT = new com.google.api.client.http.javanet.NetHttpTransport();
//        if (HTTP_TRANSPORT == null) Log.e("HTTP", "null");
//        final String spreadsheetId = "1ICOivODkrc4A86I1JVEQ0sVEa-8XriKoG5O4116xiKo";
//        final String range = "Vietnamese!A2:F";
//        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//        ValueRange response = service.spreadsheets().values()
//                .get(spreadsheetId, range)
//                .execute();
//        List<List<Object>> values = response.getValues();
//        if (values == null || values.isEmpty()) {
//            songs.add(Song.EMPTY_SONG);
//        } else {
//            Log.e("REad sheet:", "Sheet");
//            for (List row : values) {
//                int id = Integer.parseInt(row.get(0).toString());
//                String tilte = row.get(1).toString();
//                String artistName = row.get(2).toString();
//                int duration = Integer.parseInt(row.get(3).toString());
//                String pathId = row.get(4).toString();
//                String albumId = row.get(5).toString();
//                songs.add(new Song(id, tilte, artistName, duration, pathId, albumId));
//            }
//        }
//        return songs;
//    }
}