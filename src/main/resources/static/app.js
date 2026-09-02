const form = document.getElementById("preorderForm");
const resultDiv = document.getElementById("result");
const submitBtn = document.getElementById("submitBtn");
const resetBtn = document.getElementById("resetBtn");

function setLoading(isLoading) {
    submitBtn.disabled = isLoading;
    submitBtn.textContent = isLoading ? "Skickar..." : "Skicka beställning";
}

resetBtn.addEventListener("click", () => {
    document.getElementById("kl").value = 0;
    document.getElementById("kx").value = 0;
    document.getElementById("kr").value = 0;
    document.getElementById("name").value = "";
    document.getElementById("email").value = "";
    resultDiv.innerHTML = "";
});

form.addEventListener("submit", async (e) => {
    e.preventDefault();
    resultDiv.innerHTML = "";

    const kl = parseInt(document.getElementById("kl").value || "0", 10);
    const kx = parseInt(document.getElementById("kx").value || "0", 10);
    const kr = parseInt(document.getElementById("kr").value || "0", 10);
    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();

    const items = [];
    if (kl > 0) items.push({ type: "KL", quantity: kl });
    if (kx > 0) items.push({ type: "KX", quantity: kx });
    if (kr > 0) items.push({ type: "KR", quantity: kr });

    if (!name) {
        resultDiv.innerHTML = `<div class="alert alert-error">⚠️ Ange ditt namn.</div>`;
        return;
    }

    if (!email.includes("@")) {
        resultDiv.innerHTML = `<div class="alert alert-error">⚠️ Ange en giltig email.</div>`;
        return;
    }

    if (items.length === 0) {
        resultDiv.innerHTML = `<div class="alert alert-error">⚠️ Du måste beställa minst en boll.</div>`;
        return;
    }

    const payload = {
        customerName: name,
        customerEmail: email,
        items: items
    };

    try {
        setLoading(true);

        const response = await fetch("/preorders/multi", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });

        if (!response.ok) {
            let msg = "Ett oväntat fel inträffade.";
            try {
                const json = await response.json();
                msg = json.message || json.error || msg;
            } catch (_) {}

            resultDiv.innerHTML = `<div class="alert alert-error">❌ ${msg}</div>`;
            return;
        }

        const json = await response.json();
        const totalQty = items.reduce((sum, i) => sum + i.quantity, 0);

        resultDiv.innerHTML = `
    <div class="alert alert-success">
        <div class="alert-title">Order mottagen!✅ Tack för din beställning!</div>
        <div>
            Din order är registrerad och en bekräftelse skickas till 
            <strong>${email}</strong>.
        </div>
    </div>
`;
    } finally {
        setLoading(false);
    }
});
