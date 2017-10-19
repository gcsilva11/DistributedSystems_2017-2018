package RMIPackage;

import java.io.*;


public class FicheiroDeObjectos {
    
    private ObjectInputStream iS;
    private ObjectOutputStream oS;
   
    /** M�todo para abrir um ficheiro para leitura
     * @param nomeDoFicheiro - parametro com o nome do ficheiro a abrir.
     * @return
     */
    public boolean abreLeitura(String nomeDoFicheiro) {
        try{
            iS = new ObjectInputStream(new FileInputStream(nomeDoFicheiro));
            return true;
        }catch (IOException e){
            return false;
        }
    }
   
    /** M�todo para abrir um ficheiro para escrita
     * @param nomeDoFicheiro - parametro que recebe o nome do ficheiro
     * @throws IOException
     */
    public void abreEscrita(String nomeDoFicheiro) throws IOException {
        oS = new ObjectOutputStream(new FileOutputStream(new File(nomeDoFicheiro)));
    }
   
    /** M�todo para ler um objecto do ficheiro
     *
     * @return - Devolve o objecto lid
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException*/
    public Object leObjecto() throws IOException,ClassNotFoundException {
        return iS.readObject();
    }
   
    /** M�todo para escrever um objecto no ficheiro
     * @param o - Objecto a receber.
     * @throws java.io.IOException*/
    public void escreveObjecto(Object o) throws IOException {
        oS.writeObject(o);
    }
   
    /** M�todo para fechar um ficheiro aberto em modo leitura.
     * @throws IOException
     */
    public void fechaLeitura() throws IOException {
        iS.close();
    }
   
    /** M�todo para fechar um ficheiro aberto em modo escrita.
     * @throws IOException
     */
    public void fechaEscrita() throws IOException {
        oS.close();
    }

}

