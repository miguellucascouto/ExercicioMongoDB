package data;

import com.mongodb.client.*;
import model.Aluno;
import org.bson.Document;
// TODO(Alterar o método de exibição dos alunos para retornar uma lista)
// TODO(Implementar os demais métodos, alteração e exclusão de alunos)
// TODO(Implementar um método para retornar o aluno por CPF)
// TODO(Implementar filtro por ano de início)
// TODO(Atualizar o aluno baseado em seu CPF)
// TODO(IMPEDIR O CADASTRO DE ALUNOS COM MESMO CPF)
// TODO(CRIAR UMA GUI para o sistemas)


public class MongoDBConnection {
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> mongoCollection;

    public MongoDBConnection() {
        mongoClient = MongoClients.create();
        mongoDatabase = mongoClient.getDatabase("baseUniversidade");
        mongoCollection = mongoDatabase.getCollection("alunos");
    }


    public void insertAluno(Aluno aluno) {
        Document doc = new Document("nome", aluno.getNome())
                .append("curso", aluno.getCurso())
                .append("anoInicio", aluno.getAnoInicio());
        mongoCollection.insertOne(doc);
    }

    public void insertAlunoComCpf(Aluno aluno) {
        Document doc = new Document("nome", aluno.getNome())
                .append("curso", aluno.getCurso())
                .append("anoInicio", aluno.getAnoInicio())
                .append("cpf", aluno.getCpf());
        mongoCollection.insertOne(doc);
    }

    public void showAlunos() {
        MongoCursor<Document> cursor = mongoCollection.find().iterator();
        System.out.println("----------------------");
        try {
            while (cursor.hasNext()) {
                Document aluno = cursor.next();
                System.out.println("identificador: " + aluno.get("_id"));
                System.out.println("Nome: " + aluno.get("nome"));
                System.out.println("Curso: " + aluno.get("curso"));
                System.out.println("Ano de início: " + aluno.get("anoInicio"));
                System.out.println("CPF: " + obterCpf(aluno.get("cpf")));
                System.out.println("-----------------------\n");
            }
        } finally {
            cursor.close();
        }
    }

    private String obterCpf(Object cpf) {
        if (cpf == null) {
            return "-";
        } else if (!((String) cpf).isBlank()) {
            return cpf.toString();
        } else {
            return "-";
        }
    }
}
