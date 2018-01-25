package classes;

import java.util.ArrayList;

/**
 * <p>User class.</p>
 *
 */
public class User {
    private int userId;
    private String name;
    private String email;
    private String telephone;
    private String bio;
    private String relationship;
    private String gender;
    private String password;
    private String profileImage;
    private ArrayList<Debt> debts;
    private ArrayList<Debt> income;
    private ArrayList<Household> associatedHouseholds;

    /**
     * <p>Constructor for User.</p>
     */
    public User(){}

    /**
     * <p>Getter for the field <code>userId</code>.</p>
     *
     * @return a int.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * <p>Setter for the field <code>userId</code>.</p>
     *
     * @param userId a int.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    //Chore: addDebt og simplifyDebtForSingleUser er shit
    /**
     * <p>addDebt.</p>
     *
     * @param toUser a {@link classes.User} object.
     * @param sum a double.
     */
    public void addDebt(User toUser, double sum){
        if(debts.size()==0)debts.add(new Debt(sum, toUser));
        for (Debt debt :debts) {
            if(debt.getToUser().equals(toUser)){
                debt.setAmount(sum);
                return;
            }
        }
        debts.add(new Debt(sum, toUser));
    }

    /**
     * <p>addIncome.</p>
     *
     * @param toUser a {@link classes.User} object.
     * @param sum a double.
     */
    public void addIncome(User toUser, double sum){
        if(income.size()==0)income.add(new Debt(sum, toUser));
        for (Debt income :income) {
            if(income.getToUser().getUserId() == toUser.getUserId()){
                income.setAmount(sum);
                return;
            }
        }
        income.add(new Debt(sum, toUser));
    }


    /**
     * <p>simplifyDebtForSingleUser.</p>
     */
    public void simplifyDebtForSingleUser(){
        for (Debt debt : debts) {
            for(int i = 0; i<debt.getToUser().getDebts().size(); i++){
                while(debt.getAmount()>0.01){
                    if(debt.getAmount()>debt.getToUser().getDebts().get(i).getAmount()){
                        this.addDebt(debt.getToUser().getDebts().get(i).getToUser(),debt.getToUser().getDebts().get(i).getAmount());
                        debt.setAmount(debt.getAmount()-debt.getToUser().getDebts().get(i).getAmount());
                        debt.getToUser().getDebts().get(i).setAmount(0);
                    }else if(debt.getAmount()<debt.getToUser().getDebts().get(i).getAmount()){
                        //TODO: Usikker på om denne elseif-en trengs
                    }
                }
            }
        }
    }


    /**
     * <p>Getter for the field <code>debts</code>.</p>
     *
     * @return a {@link java.util.ArrayList} object.
     */
    public ArrayList<Debt> getDebts() {
        return debts;
    }

    /**
     * <p>Getter for the field <code>name</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Getter for the field <code>email</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getEmail() {
        return email;
    }

    /**
     * <p>Getter for the field <code>telephone</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getTelephone() {
        return telephone;
    }


    /**
     * <p>Getter for the field <code>gender</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getGender() {
        return gender;
    }

    /**
     * <p>Setter for the field <code>relationship</code>.</p>
     *
     * @param relationship a {@link java.lang.String} object.
     */
    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    /**
     * <p>Setter for the field <code>bio</code>.</p>
     *
     * @param bio a {@link java.lang.String} object.
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * <p>Getter for the field <code>password</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getPassword() {
        return password;
    }

    /**
     * <p>Setter for the field <code>debts</code>.</p>
     *
     * @param debts a {@link java.util.ArrayList} object.
     */
    public void setDebts(ArrayList<Debt> debts) {
        this.debts = debts;
    }

    /**
     * <p>Setter for the field <code>name</code>.</p>
     *
     * @param name a {@link java.lang.String} object.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>Setter for the field <code>email</code>.</p>
     *
     * @param email a {@link java.lang.String} object.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * <p>Setter for the field <code>telephone</code>.</p>
     *
     * @param telephone a {@link java.lang.String} object.
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    /**
     * <p>Setter for the field <code>gender</code>.</p>
     *
     * @param gender a {@link java.lang.String} object.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * <p>Getter for the field <code>relationship</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getRelationship() {
        return relationship;
    }

    /**
     * <p>Getter for the field <code>bio</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getBio() {
        return bio;
    }

    /**
     * <p>Setter for the field <code>password</code>.</p>
     *
     * @param password a {@link java.lang.String} object.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * <p>Getter for the field <code>associatedHouseholds</code>.</p>
     *
     * @return a {@link java.util.ArrayList} object.
     */
    public ArrayList<Household> getAssociatedHouseholds() {
        return associatedHouseholds;
    }

    /**
     * <p>Setter for the field <code>associatedHouseholds</code>.</p>
     *
     * @param associatedHouseholds a {@link java.util.ArrayList} object.
     */
    public void setAssociatedHouseholds(ArrayList<Household> associatedHouseholds) {
        this.associatedHouseholds = associatedHouseholds;
    }
    /**
     * <p>Getter for the field <code>profileImage</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getProfileImage() {
        return profileImage;
    }

    /**
     * <p>Setter for the field <code>profileImage</code>.</p>
     *
     * @param profileImage a {@link java.lang.String} object.
     */
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
