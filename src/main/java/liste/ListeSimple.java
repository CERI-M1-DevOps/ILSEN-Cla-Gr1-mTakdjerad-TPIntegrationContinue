package liste;

/**
 * Liste simplement chainee dont les ajouts se font en tete.
 * Les recherches d'elements utilisent l'identite des objets ({@code ==}).
 */
public class ListeSimple {
    private long size;
    Noeud tete;

    /**
     * Construit une liste vide, de taille nulle.
     */
    public ListeSimple() {
    }

    /**
     * Renvoie le nombre d'elements de la liste.
     *
     * @return la taille de la liste
     */
    public long getSize() {
        return size;
    }

    /**
     * Ajoute un entier en tete et augmente la taille de un.
     *
     * @param element entier a ajouter
     */
    public void ajout(int element) {
        tete = new Noeud(element, tete);
        size++;
    }

    /**
     * Remplace la premiere occurrence identique a l'objet recherche.
     * Ne change rien si cet objet est absent ; la taille reste inchangee.
     *
     * @param element objet recherche par identite ({@code ==})
     * @param nouvelleValeur objet de remplacement
     */
    public void modifiePremier(Object element, Object nouvelleValeur) {
        Noeud courant = tete;
        while (courant != null && courant.getElement() != element)
            courant = courant.getSuivant();
        if (courant != null)
            courant.setElement(nouvelleValeur);
    }

    /**
     * Remplace toutes les occurrences identiques a l'objet recherche.
     * La taille reste inchangee.
     *
     * @param element objet recherche par identite ({@code ==})
     * @param nouvelleValeur objet de remplacement
     */
    public void modifieTous(Object element, Object nouvelleValeur) {
        Noeud courant = tete;
        while (courant != null) {
            if (courant.getElement() == element)
                courant.setElement(nouvelleValeur);
            courant = courant.getSuivant();
        }
    }

    /**
     * Represente les noeuds dans leur ordre, de la tete a la fin.
     *
     * @return la representation {@code ListeSimple(Noeud(...), ...)},
     *         ou {@code ListeSimple()} pour une liste vide
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("ListeSimple(");
        Noeud n = tete;
        while (n != null) {
            sb.append(n);
            n = n.getSuivant();
            if (n != null)
                sb.append(", ");
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * Supprime la premiere occurrence identique a l'objet recherche
     * et diminue la taille de un. Ne change rien si l'objet est absent.
     *
     * @param element objet recherche par identite ({@code ==})
     */
    public void supprimePremier(Object element) {
        if (tete != null) {
            if (tete.getElement() == element) {
                tete = tete.getSuivant();
                size--;
                return;
            }
            Noeud precedent = tete;
            Noeud courant = tete.getSuivant();
            while (courant != null && courant.getElement() != element) {
                precedent = precedent.getSuivant();
                courant = courant.getSuivant();
            }
            if (courant != null) {
                precedent.setSuivant(courant.getSuivant());
                size--;
            }
        }
    }

    /**
     * Supprime recursivement les occurrences dont l'objet est identique
     * a l'entier converti en {@link Integer}, et ajuste la taille.
     * La comparaison porte sur les references, pas sur les valeurs numeriques.
     *
     * @param element entier converti en objet pour la recherche par identite
     */
    public void supprimeTous(int element) {
       tete = supprimeTousRecurs(element, tete);
    }

    /**
     * Filtre recursivement la chaine fournie en comparant les elements par identite.
     * Modifie les liens et diminue la taille de cette liste pour chaque suppression.
     * L'appelant doit affecter la tete retournee ; cette methode ne remplace pas
     * elle-meme le champ tete de la liste.
     *
     * @param element objet a supprimer par identite ({@code ==})
     * @param tete debut de la chaine de cette liste a filtrer, ou {@code null}
     * @return la tete de la chaine filtree, ou {@code null} si elle est vide
     */
    public Noeud supprimeTousRecurs(Object element, Noeud tete) {
        if (tete != null) {
            Noeud suiteListe = supprimeTousRecurs(element, tete.getSuivant());
            if (tete.getElement() == element) {
                size--;
                return suiteListe;
            } else {
                tete.setSuivant(suiteListe);
                return tete;
            }
        } else return null;
    }

    /**
     * Recherche le noeud situe juste avant le dernier.
     *
     * @return l'avant-dernier noeud, ou {@code null} si la liste contient
     *         moins de deux noeuds
     */
    public Noeud getAvantDernier() {
        if (tete == null || tete.getSuivant() == null)
            return null;
        else {
            Noeud courant = tete;
            Noeud suivant = courant.getSuivant();
            while (suivant.getSuivant() != null) {
                courant = suivant;
                suivant = suivant.getSuivant();
            }
            return courant;
        }
    }

    /**
     * Inverse les liens et la tete de la liste sans modifier sa taille.
     */
    public void inverser() {
        Noeud precedent = null;
        Noeud courant = tete;
        while (courant != null) {
            Noeud next = courant.getSuivant();
            courant.setSuivant(precedent);
            precedent = courant;
            courant = next;
        }
        tete = precedent;
    }

    /**
     * Recherche le predecesseur d'un noeud de la liste.
     * La liste doit etre non vide et le noeud fourni doit lui appartenir
     * sans etre sa tete ; ces preconditions ne sont pas verifiees.
     *
     * @param r noeud non nul de la liste, different de la tete
     * @return le noeud qui precede {@code r}
     */
    public Noeud getPrecedent(Noeud r) {
    // la liste n'est pas vide puisqu'on transmet un Node de la liste et le Node existe obligatoirement
        Noeud precedent = tete;
        Noeud courant = precedent.getSuivant();
        while (courant != r) {
            precedent = courant;
            courant = courant.getSuivant();
        }
        return precedent;
    }

    /**
     * Echange les positions de deux noeuds sans modifier la taille.
     * Deux references identiques ne provoquent aucune modification.
     * Sinon, les deux noeuds doivent etre non nuls et appartenir a cette liste.
     *
     * @param r1 premier noeud a echanger
     * @param r2 second noeud a echanger
     */
    public void echanger(Noeud r1, Noeud r2) {
        if (r1 == r2)
            return;
        Noeud precedentR1;
        Noeud precedentR2;
        if (r1 != tete && r2 != tete) {
            precedentR1 = getPrecedent(r1);
            precedentR2 = getPrecedent(r2);
            precedentR1.setSuivant(r2);
            precedentR2.setSuivant(r1);
        } else if (r1 == tete) {
            precedentR2 = getPrecedent(r2);
            precedentR2.setSuivant(tete);
            tete = r2;
        }
        else {
            precedentR1 = getPrecedent(r1);
            precedentR1.setSuivant(tete);
            tete = r1;
        }
        Noeud temp = r2.getSuivant();
        r2.setSuivant(r1.getSuivant());
        r1.setSuivant(temp);
    }

}
