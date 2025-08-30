const formulario = document.querySelector("form");

const Icpf = document.querySelector("#cpf");
const Iusername = document.querySelector("#username");
const InomeCompleto = document.querySelector("#nomeCompleto");
const Iemail = document.querySelector("#email");
const ItelefoneCelular = document.querySelector("#telefoneCelular");
const Ipassword = document.querySelector("#password");

const dadosJSON = {
  cpf: Icpf.value,
  username: Iusername.value,
  nomeCompleto: InomeCompleto.value,
  email: Iemail.value,
  telefoneCelular: ItelefoneCelular.value,
  password: Ipassword.value,
};

function cadastrarDados() {
  fetch("http://localhost:8080/cadastrarUsuario", {
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
    },
    method: "POST",
    body: JSON.stringify(dadosJSON),
  })
    .then(function (res) {
      console.log(res);
    })
    .catch(function (res) {
      console.log(res);
    });
}

function limparDados() {
  Icpf.value = "";
  Iusername.value = "";
  InomeCompleto.value = "";
  Iemail.value = "";
  ItelefoneCelular.value = "";
  Ipassword.value = "";
}

formulario.addEventListener("submit", function (event) {
  event.preventDefault();

  cadastrarDados();
  limparDados();
});
