document.getElementById("loginForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const email = document.getElementById("userEmail").value;
    const password = document.getElementById("userPassword").value;

    const loginPayload = {
        userEmail: email,
        userPassword: password
    };

    try {
        const response = await fetch("/userservice/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(loginPayload)
        });

        if (!response.ok) {
            alert("Login failed. Please check your credentials.");
            return;
        }

        const result = await response.json();

        const token = result.token; // make sure backend returns {"token": "JWT_HERE"}
        if (token) {
            localStorage.setItem("jwtToken", token);
            window.location.href = "/User-Dashboard.html";
        } else {
            alert("Invalid login response. No token found.");
        }

    } catch (error) {
        console.error("Login error:", error);
        alert("An error occurred. Try again.");
    }
});
