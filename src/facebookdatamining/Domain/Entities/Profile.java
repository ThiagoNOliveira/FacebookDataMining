package facebookdatamining.Domain.Entities;

/**
 *
 * @author Thiago N. Oliveira
 */
public class Profile {

    private long Id;
    private String Name;
    private String About;
    
    //Basic Info
    private String Birthday;
    private String Sex;
    private String InterestedIn;
    private String ReltionshipStatus;
    private String ReligiousView;
    private String PoliticalViews;
    
    //Contact Info
    private String Emails;    
    private String MobilePhones;
    private String OtherPhones;
    private String IMScreenNames;
    private String Address;
    private String WebSites;
    private String Networks;
    
    //Work and Education
    private String Work;
    private String College;
    private String HighSchool;
    
    //Living
    private String CurrentCity;
    private String Hometown;
    
    //Family
    private String Family;
    
    //Favorites
    private String Books;
    private String Music;
    private String Movies;
    private String Televisions;
    private String Activities;
    private String Interests;
    
    //Timeline
    private String QuantityOfPhotos;
    private String QuantityOfFriends;
}
