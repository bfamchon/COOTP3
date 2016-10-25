import java.sql.SQLException;
import java.util.List;

import com.cf.domain.Bureau;
import com.cf.domain.Personne;
import com.cf.persistence.BureauMapper;
import com.cf.persistence.InterfaceMapper;
import com.cf.persistence.PersonneMapper;
import com.cf.persistence.gestionnaireconnexion.DBConfig;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InterfaceMapper<Bureau> bMapper = new BureauMapper();
		InterfaceMapper<Personne> pMapper = new PersonneMapper();
		try {
			List<Bureau> b = bMapper.find();
			/*ServicesMetier sm = new ServicesMetier();
			List<Personne> listPers=sm.listerPersonnesDansBureau(0);

			Personne p = new Chercheur(999,"NoName","1111111111",".org");
			pMapper.insert(p);

			sm.changerNumPersonne(p,"0000000000");
			pMapper.delete(p);

			sm.enleverPersonneBureau(9);
			sm.affecterPersonneBureau(9,3);*/
//			List<Bureau> listBureau = bMapper.find();
//			List<Personne> listPers = pMapper.find();

////			Test update OK
//			Personne p = new Administratif(0,"Test","0303030303","TEST","TEST");
//			pMapper.update(p);

////			Test insertion OK
//			Personne p = new Chercheur(0,"Test","0321108993","TEST");
//			pMapper.insert(p);
//			Bureau b=new Bureau(1,"Le bureau 1");
//			bMapper.insert(b);

////			Test suppressions OK ( remplir base avant > script sql/famchon )
//			System.out.println("Modification BDD - delete()");
//			for (Personne stkP: listPers) {
//				pMapper.delete(stkP);
//			}
//			for (Bureau stkB: listBureau) {
//				bMapper.delete(stkB);
//			}
//			bMapper.delete(b);

////			Test des retours OK
//			for (Personne stkP: listPers) {
//				System.out.println(stkP.toString());
//			}
//			for (Bureau stkB: listBureau) {
//				System.out.println(stkB.toString());
//			}

			System.out.println("fin");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBConfig.getInstance().fermerConnexion();
	}

}
/*
*Personne mapper implémenté
Bureau mapper implémenté
Ajout de quelques commentaires ( + Remarques à voir )

Implémentation de quelques services métier ( à voir car j'y ai fait des requetes sql > update téléphone par exemple, impossible à faire dans les filles d'une interface !

J'ai séparé les classes
 */