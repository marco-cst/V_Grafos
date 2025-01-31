from flask import Flask, json, flash, Blueprint, url_for, jsonify, make_response, request, render_template, redirect, abort
import requests
from urllib.parse import quote

router = Blueprint('router', __name__)

@router.route('/admin')
def admin():
    return render_template('tmpl/lista.html')

@router.route('/grafo')
def grafo():
    return render_template("tmpl/grafo.html")

@router.route('/admin/universidad/list')
def list_sintetica():
    r = requests.get("http://localhost:8075/api/universidad/list")
    data = r.json()
    return render_template('tmpl/lista.html', list=data["data"])

@router.route('/admin/universidad/edit/<int:id>')
def view_edit_sintetica(id):
    r = requests.get("http://localhost:8075/api/universidad/list")
    data = r.json()
    r1 = requests.get(f"http://localhost:8075/api/universidad/get/{id}")
    data1 = r1.json()
    
    if r1.status_code == 200:
        return render_template('tmpl/editar.html', list=data["data"], sintetica=data1["data"])
    else:
        flash("sintetica no encontrado", category='error')
        return redirect("/admin/universidad/list")

@router.route('/admin/universidad/register')
def view_register_sintetica():
    r = requests.get("http://localhost:8075/api/universidad/list")
    data = r.json()
    return render_template('tmpl/registro.html', list=data["data"])

@router.route('/admin/universidad/save', methods=["POST"])
def save_sintetica():
    headers = {'Content-type': 'application/json'}
    form = request.form
    print("Datos del formulario:", form)
    try:
        dataF = {
            "nombre": form["nombre"],
            "latitud": float(form["latitud"]),
            "longitud": float(form["longitud"]),
            "tipo": form["tipo"],
        }
        print("Datos enviados al backend:", dataF) 
        r = requests.post("http://localhost:8075/api/universidad/save", data=json.dumps(dataF), headers=headers)
        dat = r.json()
        print("Respuesta de la API:", dat)
        if r.status_code == 200:
            flash("Sintética guardada correctamente", category='info')
        else:
            flash("Error al guardar la sintética", category='error')

    except ValueError:
        flash("Error: La latitud y longitud deben ser valores numéricos", category='error')

    return redirect(url_for("router.list_sintetica"))


@router.route('/admin/universidad/delete/<int:id>', methods=['DELETE'])
def delete_sintetica(id):
    try:
        response = requests.delete(f"http://localhost:8075/api/universidad/delete/{id}")
        if response.status_code == 200:
            flash("sintetica eliminado exitosamente", "info")
            return jsonify({"message": "sintetica eliminado exitosamente."}), 200  # Responder con JSON
        else:
            flash("Error al eliminar el sintetica.", "danger")
            return jsonify({"error": "No se pudo eliminar el sintetica."}), response.status_code
    except Exception as e:
        flash(f"Hubo un error: {str(e)}", "danger")
        return jsonify({"error": str(e)}), 500  


@router.route('/admin/universidad/update', methods=["POST"])
def update_sintetica():
    headers = {'Content-type': 'application/json'}
    form = request.form
    dataF = {
        "idSintetica": form["id"],
        "nombre": form["nombre"],
            "latitud": float(form["latitud"]),
            "longitud": float(form["longitud"]), 
            "tipo": form["tipo"],
    }
    r = requests.post("http://localhost:8075/api/universidad/update", data=json.dumps(dataF), headers=headers)
    dat = r.json()
    
    if r.status_code == 200:
        flash("sintetica actualizado correctamente", category='info')
       
    else:
        flash(str(dat["data"]), category='error')

    return redirect("/admin/universidad/list")