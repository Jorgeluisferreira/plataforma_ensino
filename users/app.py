from flask import Flask, request, jsonify # type: ignore
from flask_cors import CORS # type: ignore
import psycopg2 # type: ignore

app = Flask(__name__)
CORS(app)

# Conexão com PostgreSQL
conn = psycopg2.connect(
    host="db",  # 'db' é o nome do serviço no docker-compose
    database="users_db",
    user="admin",
    password="admin123",
    port="5432"
)
cur = conn.cursor()

# Garante que a tabela existe
cur.execute("""
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);
""")
conn.commit()


#Criar usuário
@app.route('/users', methods=['POST'])
def create_user():
    data = request.json
    name = data['name']
    email = data['email']

    try:
        cur.execute("""
            INSERT INTO users (name, email)
            VALUES (%s, %s)
            RETURNING id;
        """, (name, email))
        user_id = cur.fetchone()[0]
        conn.commit()
        return jsonify({'message': 'Usuário criado', 'id': user_id}), 201
    except Exception as e:
        conn.rollback()
        return jsonify({'error': str(e)}), 400


#Ler todos os usuários
@app.route('/users', methods=['GET'])
def get_users():
    cur.execute("SELECT * FROM users;")
    users = cur.fetchall()
    user_list = []
    for u in users:
        user_list.append({'id': u[0], 'name': u[1], 'email': u[2]})
    return jsonify(user_list)


#Ler usuário por ID
@app.route('/users/<int:user_id>', methods=['GET'])
def get_user(user_id):
    cur.execute("SELECT * FROM users WHERE id = %s;", (user_id,))
    user = cur.fetchone()
    if user:
        return jsonify({'id': user[0], 'name': user[1], 'email': user[2]})
    else:
        return jsonify({'error': 'Usuário não encontrado'}), 404


#Atualizar usuário
@app.route('/users/<int:user_id>', methods=['PUT'])
def update_user(user_id):
    data = request.json
    name = data.get('name')
    email = data.get('email')

    query = "UPDATE users SET "
    fields = []
    values = []

    if name:
        fields.append("name = %s")
        values.append(name)
    if email:
        fields.append("email = %s")
        values.append(email)

    if not fields:
        return jsonify({'error': 'Nenhum dado para atualizar'}), 400

    query += ", ".join(fields)
    query += " WHERE id = %s"
    values.append(user_id)

    try:
        cur.execute(query, tuple(values))
        conn.commit()
        return jsonify({'message': 'Usuário atualizado com sucesso'})
    except Exception as e:
        conn.rollback()
        return jsonify({'error': str(e)}), 400


#Deletar usuário
@app.route('/users/<int:user_id>', methods=['DELETE'])
def delete_user(user_id):
    try:
        cur.execute("DELETE FROM users WHERE id = %s;", (user_id,))
        conn.commit()
        return jsonify({'message': 'Usuário deletado com sucesso'})
    except Exception as e:
        conn.rollback()
        return jsonify({'error': str(e)}), 400

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8000)
