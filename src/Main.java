import java.sql.SQLException;
import java.util.List;

import com.cf.domain.Bureau;
import com.cf.domain.Chercheur;
import com.cf.domain.Personne;
import com.cf.metier.SMGestionnaireBureau;
import com.cf.metier.SMGestionnairePersonne;

public class Main {
	public static void main(String[] args) {
		SMGestionnaireBureau gestionnaireBureau = new SMGestionnaireBureau();
		SMGestionnairePersonne gestionnairePersonne = new SMGestionnairePersonne();
		try {
			Personne p = new Chercheur("Laurent", "060606003", "Informatique");
			Bureau b = new Bureau("Bureau de Laurent");
			gestionnairePersonne.ajouterPersonne(p);

			gestionnaireBureau.affecterPersonneBureau(p, b);

		/*
		 * A partir de ce moment, l'objet b "n'est plus le meme" car il est charge depuis la base,
		 * donc dans un autre objet Bureau (autre zone mémoire). Pour contrer ce probleme, il peut etre interessant
		 * d'utiliser les weak references / hash-map
		 */
			List<Bureau> listeBureaux = gestionnaireBureau.ListerBureau();

			for (Bureau bureau : listeBureaux) {
				System.out.println(bureau.toString());
			}

			gestionnaireBureau.enleverPersonneBureau(p, listeBureaux.get(0));

			for (Bureau bureau : listeBureaux) {
				System.out.println(bureau.toString());
			}

			listeBureaux = gestionnaireBureau.ListerBureau();

			for (Bureau bureau : listeBureaux) {
				System.out.println(bureau.toString());
			}

			gestionnairePersonne.changerNumPersonne(p, "0101010101");

			Personne personneRecherche = gestionnairePersonne.rechercherPersonneByTel("0101010101", listeBureaux);

			System.out.println(personneRecherche.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}