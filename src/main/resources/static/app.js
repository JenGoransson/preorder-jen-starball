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
    const name = document.getElementById("customerName").value.trim();
    const email = document.getElementById("customerEmail").value.trim();
    const street = document.getElementById("street").value.trim();
    const postalCode = document.getElementById("postalCode").value.trim();
    const city = document.getElementById("city").value.trim();

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
        street: street,
        postalCode: postalCode,
        city: city,
        items: items
    };

    try {
        setLoading(true);

        const response = await fetch("https://preorder-jen-starball.onrender.com/preorders/multi", {
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

        const orderedItemsText = items
            .map(i => `${i.quantity} st ${i.type}`)
            .join(", ");

        resultDiv.innerHTML = `
    <div class="alert alert-success">
        <div class="alert-title">Order mottagen! ✅</div>

        <p>
            Tack <strong>${name}</strong> för din beställning av 
            <strong>${orderedItemsText}</strong>.
        </p>


        <p>
            Om något blivit fel i din beställning, släng iväg ett mail till:
            <strong>order.jennifer.goransson@gmail.com</strong>
        </p>

        <p>
            Ett bekräftelsemail kommer skickas till den angivna epost-adressen inom 24h.
        </p>
    </div>
`;

    } finally {
        setLoading(false);
    }
});
