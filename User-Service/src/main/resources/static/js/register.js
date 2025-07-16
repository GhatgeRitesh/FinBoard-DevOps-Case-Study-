document.getElementById('registerForm').addEventListener('submit', async function (e) {
  e.preventDefault();

  const loader = document.getElementById('loader');
  const responseMsg = document.getElementById('responseMsg');
  loader.classList.remove('hidden');
  responseMsg.innerText = "";
  responseMsg.style.color = "#333";

  const formData = new FormData(e.target);
  const data = Object.fromEntries(formData.entries());

  try {
    const res = await fetch('/userservice/registration', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    });

    if (res.status !== 204) {
      const result = await res.json();
      if (res.ok) {
        responseMsg.innerText = "✅ Registration successful!";
        responseMsg.style.color = "green";
        e.target.reset();
      } else {
        throw new Error(result.message || "Something went wrong.");
      }
    } else {
      responseMsg.innerText = "✅ Registered successfully!";
      responseMsg.style.color = "green";
      e.target.reset();
    }

  } catch (err) {
    responseMsg.innerText = "❌ " + err.message;
    responseMsg.style.color = "red";
  } finally {
    loader.classList.add('hidden');
  }
});
