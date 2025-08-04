package dsignpattern.creational;

public class Prototype {

    public static void main(String[] args) {
        // this is the prototype object
        Resume resume = new Resume("Markandey Kumar", "Java Developer with 4 years of experience.");

        // this is the cloned object
        Resume clonedResume = (Resume) resume.clone();

        System.out.println("resume  == clonedResume "+ (resume == clonedResume));

        // change the content of cloned resume

        clonedResume.setContent("Java Backend Developer - Franconnect");

    }
}

// cloning of an object instead of creating a new object

//scenarios where object creation is costly ex :

//


// Prototype Interface
interface Document extends Cloneable {
    Document clone();
    void display();
}


class Resume implements Document{

    private String name;
    private String content;

    public Resume(String name, String content) {
        this.name = name;
        this.content = content;
    }

    @Override
    public Document clone() {
        return new Resume(this.name, this.content);
    }

    @Override
    public void display() {
        System.out.println("Resume: " + name);
        System.out.println("Content: " + content);
    }

    public void setContent(String content) {
        this.content = content;
    }
}

/**
 * 1. Email template ,   do not create and compose tempalte from scratch , that will be time consuming
 * instead have a base template and change required places tp send meesages
 */


/**
 *
 * 2. Vehicle Prototyping System
 * 💡 Automobile companies often build concept car prototypes. Instead of building each version from scratch, engineers clone an existing model and modify it.
 *
 * Key Flow:
 * Base Model → Clone → Customize → Final Car Design
 *
 */

/**
 * 3. Game Character Customization
 * 💡 In multiplayer games like PUBG or Fortnite, players often start with a base character, which they can clone and modify (e.g., different armor, clothing, or abilities).
 *
 * Example Process:
 *
 * Base Warrior → Clone → Customize Weapons, Armor, Skills
 */