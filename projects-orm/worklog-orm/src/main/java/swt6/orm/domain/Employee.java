package swt6.orm.domain;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "employee_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("E")
//@Table(name = "EMPL_TABLE")
public class Employee implements Serializable {
    @Id @GeneratedValue
    private Long id;
    //@Column(name = "F_N", nullable = false)
    private String firstName;
    private String lastName;
    //@Transient
    private LocalDate dateOfBirth;
    @OneToMany(cascade={CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "employee", orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<LogbookEntry> logBookEntries = new HashSet<>();
    //@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @Embedded
    @AttributeOverride(name = "zipCode", column = @Column(name = "postalCode"))
    private Address address;
    @ElementCollection
    @CollectionTable(name = "EMPL_PHONES", joinColumns = @JoinColumn(name = "EMPL_ID"))
    @Column(name = "PHONE_NR")
    private Set<String> phones = new HashSet<>();
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "members")
    private Set<Project> projects = new HashSet<>();

    public Employee() {
    }

    public Employee(String firstName, String lastName, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public void assignToproject(Project project) {
        this.projects.add(project);
        project.getMembers().add(this);
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<LogbookEntry> getLogBookEntries() {
        return logBookEntries;
    }

    public void setLogBookEntries(Set<LogbookEntry> logBookEntries) {
        this.logBookEntries = logBookEntries;
    }

    public void addLogBookEntry(LogbookEntry entry) {
        if (entry.getEmployee() != null) {
            entry.getEmployee().getLogBookEntries().remove(entry);
        }
        logBookEntries.add(entry);
        entry.setEmployee(this);
    }

    public void removeLogBookEntry(LogbookEntry entry) {
        this.logBookEntries.remove(entry);
    }

    public Set<String> getPhones() {
        return phones;
    }

    public void setPhones(Set<String> phones) {
        this.phones = phones;
    }

    public void addPhone(String p) {
        this.phones.add(p);
    }

    @Override
    public String toString() {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        var sb = new StringBuilder();
        sb.append(id + ":" + lastName + " " + firstName + " (" + dateOfBirth.format(formatter) + " )");
        sb.append(address.toString());
        return sb.toString();
    }
}
