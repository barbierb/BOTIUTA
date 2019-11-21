package fr.itspower.botiuta.student;

@SuppressWarnings("unused")
public class User {
	
	private final long ID;
	
	private Groupe groupe;
	private String username;
	private long firstJoin; // non utilisé
	private long birthdate; // non utilisé

	/**
	 * Construit un utilisateur
	 * 
	 * @param id - l'identifiant utilisateur
	 */
	public User(long id) {
		this.ID = id;
		this.groupe = Groupe.AUNCUN;
	}
	
	/**
	 * Construit un utilisateur
	 * 
	 * @param id - l'identifiant utilisateur
	 * @param groupe - le groupe si l'utilisateur en a déjà choisi un
	 */
	public User(long id, Groupe groupe) {
		this.ID = id;
		this.groupe = groupe;
	}

	/**
	 * @return l'identifiant généré de l'utilisateur.
	 */
	public long getID() {
		return ID;
	}
	
	
	
	
}
